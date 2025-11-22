package pl.webd.dawid124.ioengine.module.monitoring.model.rest;

import pl.webd.dawid124.ioengine.module.monitoring.model.MonitoringEvent;

import java.util.List;

public class SearchResponse {

    private List<MonitoringEvent> events;
    private int total;
    private int offset;
    private int limit;

    public SearchResponse() {}

    public SearchResponse(List<MonitoringEvent> events, int total, int offset, int limit) {
        this.events = events;
        this.total = total;
        this.offset = offset;
        this.limit = limit;
    }

    public List<MonitoringEvent> getEvents() {
        return events;
    }

    public void setEvents(List<MonitoringEvent> events) {
        this.events = events;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
