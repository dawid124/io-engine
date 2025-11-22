package pl.webd.dawid124.ioengine.module.monitoring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.monitoring.model.ChildMonitoringDay;
import pl.webd.dawid124.ioengine.module.monitoring.model.MonitoringEvent;
import pl.webd.dawid124.ioengine.module.monitoring.model.rest.MonitoringEventRequest;
import pl.webd.dawid124.ioengine.module.monitoring.repository.MonitoringRepository;

import javax.annotation.PreDestroy;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Buffered monitoring service that collects events in memory
 * and flushes them to SQLite database periodically to reduce I/O operations.
 *
 * Optimized for Raspberry Pi with batch inserts and minimal writes.
 */
@Service
public class BufferedMonitoringService {

    private static final Logger log = LoggerFactory.getLogger(BufferedMonitoringService.class);
    private static final int FLUSH_INTERVAL_MS = 10000; // 10 seconds

    private final MonitoringRepository repository;

    // Map: "deviceId_date" -> List of events
    private final Map<String, List<MonitoringEvent>> buffer = new ConcurrentHashMap<>();
    private final Map<String, DayInfo> dayInfoMap = new ConcurrentHashMap<>();

    public BufferedMonitoringService(MonitoringRepository repository) {
        this.repository = repository;
    }

    /**
     * Add event to buffer (fast, in-memory operation)
     */
    public void logEvent(MonitoringEventRequest request) {
        try {
            // Determine date from timestamp
            LocalDate date = Instant.ofEpochMilli(request.getTimestamp())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            String dateStr = date.toString();

            // Generate key
            String dayKey = request.getDeviceId() + "_" + dateStr;

            // Store day info if not present
            dayInfoMap.putIfAbsent(dayKey, new DayInfo(
                    dayKey,
                    request.getDeviceId(),
                    request.getChildName() != null ? request.getChildName() : "Child",
                    dateStr
            ));

            // Create monitoring event
            MonitoringEvent event = new MonitoringEvent(
                    UUID.randomUUID().toString(),
                    request.getDeviceId(),
                    request.getTimestamp(),
                    request.getType(),
                    request.getApp(),
                    request.getAppName(),
                    request.getSender(),
                    request.getReceiver(),
                    request.getContent(),
                    request.getConversationId()
            );

            // Add to buffer (thread-safe)
            buffer.computeIfAbsent(dayKey, k -> Collections.synchronizedList(new ArrayList<>()))
                    .add(event);

            log.debug("Event buffered for {}: {} events in buffer", dayKey, buffer.get(dayKey).size());

        } catch (Exception e) {
            log.error("Failed to buffer monitoring event", e);
        }
    }

    /**
     * Flush buffer to disk every 10 seconds
     */
    @Scheduled(fixedRate = FLUSH_INTERVAL_MS)
    public void flushBuffer() {
        if (buffer.isEmpty()) {
            return;
        }

//        log.info("Flushing buffer: {} days with events", buffer.size());

        // Create snapshot and clear buffer
        Map<String, List<MonitoringEvent>> snapshot = new HashMap<>();
        buffer.forEach((key, events) -> {
            if (!events.isEmpty()) {
                snapshot.put(key, new ArrayList<>(events));
            }
        });

        // Clear buffer after snapshot
        snapshot.keySet().forEach(key -> buffer.get(key).clear());

        // Write to disk
        snapshot.forEach((dayKey, events) -> {
            try {
                writeEventsToFile(dayKey, events);
                log.debug("Flushed {} events for {}", events.size(), dayKey);
            } catch (Exception e) {
                log.error("Failed to flush events for " + dayKey, e);
                // Re-add events to buffer on failure
                buffer.computeIfAbsent(dayKey, k -> Collections.synchronizedList(new ArrayList<>()))
                        .addAll(events);
            }
        });
    }

    /**
     * Write events to SQLite database (batch insert)
     */
    private void writeEventsToFile(String dayKey, List<MonitoringEvent> newEvents) {
        DayInfo info = dayInfoMap.get(dayKey);
        if (info == null) {
            log.warn("No day info for key: {}", dayKey);
            return;
        }

        // Batch insert to SQLite (fast!)
        repository.batchInsert(info.deviceId, info.childName, info.date, newEvents);

        log.info("Saved {} events to database: {}", newEvents.size(), dayKey);
    }

    /**
     * Get current day (combines database + buffer)
     */
    public ChildMonitoringDay getDay(String deviceId, String date) {
        // Load from database
        List<MonitoringEvent> dbEvents = repository.findByDeviceAndDate(deviceId, date);

        // Create day object
        String dayKey = deviceId + "_" + date;
        DayInfo info = dayInfoMap.get(dayKey);
        ChildMonitoringDay day = new ChildMonitoringDay(
                dayKey,
                deviceId,
                info != null ? info.childName : "Child",
                date
        );

        // Add events from database
        dbEvents.forEach(day::addEvent);

        // Add buffered events (not yet flushed)
        List<MonitoringEvent> bufferedEvents = buffer.get(dayKey);
        if (bufferedEvents != null && !bufferedEvents.isEmpty()) {
            synchronized (bufferedEvents) {
                bufferedEvents.forEach(day::addEvent);
            }
        }

        return day;
    }

    /**
     * Flush buffer on application shutdown
     */
    @PreDestroy
    public void shutdown() {
        log.info("Shutting down - flushing remaining events");
        flushBuffer();
    }

    /**
     * Helper class to store day metadata
     */
    private static class DayInfo {
        final String id;
        final String deviceId;
        final String childName;
        final String date;

        DayInfo(String id, String deviceId, String childName, String date) {
            this.id = id;
            this.deviceId = deviceId;
            this.childName = childName;
            this.date = date;
        }
    }
}
