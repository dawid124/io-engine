package pl.webd.dawid124.ioengine.module.monitoring.service;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.monitoring.model.ChildMonitoringDay;
import pl.webd.dawid124.ioengine.module.monitoring.model.MonitoringEvent;
import pl.webd.dawid124.ioengine.module.monitoring.model.rest.MonitoringEventRequest;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

@Service
public class MonitoringService {

    private final MonitoringFileService fileService;
    private final Object lock = new Object();

    public MonitoringService(MonitoringFileService fileService) {
        this.fileService = fileService;
    }

    public void logEvent(MonitoringEventRequest request) {
        try {
            // Determine date from timestamp
            LocalDate date = Instant.ofEpochMilli(request.getTimestamp())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            String dateStr = date.toString();  // "2025-11-20"

            // Generate ID for the day
            String dayId = request.getDeviceId() + "_" + dateStr;

            // Synchronize to prevent race conditions when multiple requests come for the same day
            synchronized (lock) {
                // Get or create monitoring day
                ChildMonitoringDay day = getOrCreateDay(dayId, request.getDeviceId(), request.getChildName(), dateStr);

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

                // Add event to day
                day.addEvent(event);

                // Save to file (all previous events + new event)
                fileService.save(day);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save monitoring event", e);
        }
    }

    private ChildMonitoringDay getOrCreateDay(String dayId, String deviceId, String childName, String date) throws IOException {
        ChildMonitoringDay day = fileService.load(deviceId, date);

        if (day == null) {
            day = new ChildMonitoringDay(dayId, deviceId, childName, date);
        }

        return day;
    }

    public ChildMonitoringDay getDay(String deviceId, String date) {
        try {
            return fileService.load(deviceId, date);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load monitoring day", e);
        }
    }
}
