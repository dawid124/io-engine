package pl.webd.dawid124.ioengine.module.monitoring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.webd.dawid124.ioengine.module.monitoring.model.ChildMonitoringDay;
import pl.webd.dawid124.ioengine.module.monitoring.model.rest.MonitoringEventRequest;
import pl.webd.dawid124.ioengine.module.monitoring.model.rest.SearchRequest;
import pl.webd.dawid124.ioengine.module.monitoring.model.rest.SearchResponse;
import pl.webd.dawid124.ioengine.module.monitoring.service.BufferedMonitoringService;
import pl.webd.dawid124.ioengine.module.monitoring.service.MonitoringSearchService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/monitoring")
public class MonitoringController {

    private final BufferedMonitoringService monitoringService;
    private final MonitoringSearchService searchService;

    public MonitoringController(BufferedMonitoringService monitoringService, MonitoringSearchService searchService) {
        this.monitoringService = monitoringService;
        this.searchService = searchService;
    }

    /**
     * Log a monitoring event
     * POST /api/monitoring/event
     */
    @PostMapping(value = "/event", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> logEvent(@RequestBody MonitoringEventRequest request) {
        monitoringService.logEvent(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Get monitoring data for a specific day
     * GET /api/monitoring/day?deviceId=xxx&date=2025-11-20
     */
    @GetMapping(value = "/day", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ChildMonitoringDay> getDay(
            @RequestParam String deviceId,
            @RequestParam String date) {
        ChildMonitoringDay day = monitoringService.getDay(deviceId, date);
        if (day == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(day);
    }

    /**
     * Search monitoring events with filters
     * GET /api/monitoring/search?deviceId=xxx&dateFrom=2025-11-01&dateTo=2025-11-20&searchText=...
     * Note: deviceId, dateFrom, and dateTo are now optional
     */
    @GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<SearchResponse> search(
            @RequestParam(required = false) String deviceId,
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo,
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) String sender,
            @RequestParam(required = false) String app,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "100") int limit,
            @RequestParam(defaultValue = "0") int offset) {

        SearchRequest request = new SearchRequest();
        request.setDeviceId(deviceId);
        request.setDateFrom(dateFrom);
        request.setDateTo(dateTo);
        request.setSearchText(searchText);
        request.setSender(sender);
        request.setApp(app);
        request.setType(type);
        request.setLimit(limit);
        request.setOffset(offset);

        SearchResponse response = searchService.search(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Alternative POST endpoint for search (for complex queries)
     * POST /api/monitoring/search
     */
    @PostMapping(value = "/search", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<SearchResponse> searchPost(@RequestBody SearchRequest request) {
        SearchResponse response = searchService.search(request);
        return ResponseEntity.ok(response);
    }
}
