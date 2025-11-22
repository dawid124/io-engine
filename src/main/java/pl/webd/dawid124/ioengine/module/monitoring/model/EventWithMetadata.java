package pl.webd.dawid124.ioengine.module.monitoring.model;

public class EventWithMetadata {
    private final String deviceId;
    private final String childName;
    private final String date;
    private final MonitoringEvent event;

    public EventWithMetadata(String deviceId, String childName, String date, MonitoringEvent event) {
        this.deviceId = deviceId;
        this.childName = childName;
        this.date = date;
        this.event = event;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getChildName() {
        return childName;
    }

    public String getDate() {
        return date;
    }

    public MonitoringEvent getEvent() {
        return event;
    }
}
