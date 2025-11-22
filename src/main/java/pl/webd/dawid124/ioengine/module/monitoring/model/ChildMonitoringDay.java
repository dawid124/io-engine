package pl.webd.dawid124.ioengine.module.monitoring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChildMonitoringDay implements Serializable {

    private String id;  // Format: "device123_2025-11-20"
    private String deviceId;
    private String childName;
    private String date;  // "2025-11-20"
    private List<MonitoringEvent> events;

    public ChildMonitoringDay() {
        this.events = new ArrayList<>();
    }

    public ChildMonitoringDay(String id, String deviceId, String childName, String date) {
        this.id = id;
        this.deviceId = deviceId;
        this.childName = childName;
        this.date = date;
        this.events = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<MonitoringEvent> getEvents() {
        return events;
    }

    public void setEvents(List<MonitoringEvent> events) {
        this.events = events;
    }

    public void addEvent(MonitoringEvent event) {
        if (this.events == null) {
            this.events = new ArrayList<>();
        }
        this.events.add(event);
    }
}
