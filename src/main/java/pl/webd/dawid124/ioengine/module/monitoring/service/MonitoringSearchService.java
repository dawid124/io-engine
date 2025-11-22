package pl.webd.dawid124.ioengine.module.monitoring.service;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.monitoring.model.MonitoringEvent;
import pl.webd.dawid124.ioengine.module.monitoring.model.rest.SearchRequest;
import pl.webd.dawid124.ioengine.module.monitoring.model.rest.SearchResponse;
import pl.webd.dawid124.ioengine.module.monitoring.repository.MonitoringRepository;

import java.util.List;

/**
 * Search service using SQLite database with indexed queries.
 * Much faster than file-based search thanks to database indexes.
 */
@Service
public class MonitoringSearchService {

    private final MonitoringRepository repository;

    public MonitoringSearchService(MonitoringRepository repository) {
        this.repository = repository;
    }

    /**
     * Search monitoring events with filters using optimized SQL queries
     */
    public SearchResponse search(SearchRequest criteria) {
        // Get total count for pagination
        int total = repository.countSearch(
                criteria.getDeviceId(),
                criteria.getDateFrom(),
                criteria.getDateTo(),
                criteria.getSearchText(),
                criteria.getSender(),
                criteria.getApp(),
                criteria.getType()
        );

        // Get paginated results
        List<MonitoringEvent> events = repository.search(
                criteria.getDeviceId(),
                criteria.getDateFrom(),
                criteria.getDateTo(),
                criteria.getSearchText(),
                criteria.getSender(),
                criteria.getApp(),
                criteria.getType(),
                criteria.getLimit(),
                criteria.getOffset()
        );

        return new SearchResponse(events, total, criteria.getOffset(), criteria.getLimit());
    }
}
