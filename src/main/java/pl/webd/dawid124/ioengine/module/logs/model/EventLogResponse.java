package pl.webd.dawid124.ioengine.module.logs.model;

import java.util.List;

public class EventLogResponse {
    private List<EventLog> logs;

    public EventLogResponse(List<EventLog> logs) {
        this.logs = logs;
    }

    public List<EventLog> getLogs() {
        return logs;
    }

    public void setLogs(List<EventLog> logs) {
        this.logs = logs;
    }
}
