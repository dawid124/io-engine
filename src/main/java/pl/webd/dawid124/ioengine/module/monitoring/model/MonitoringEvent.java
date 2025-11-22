package pl.webd.dawid124.ioengine.module.monitoring.model;

import java.io.Serializable;

public class MonitoringEvent implements Serializable {

    private String id;
    private String deviceId;
    private long timestamp;
    private String type;  // MESSAGE, TEXT_INPUT, APP_OPENED, SCREENSHOT
    private String app;
    private String appName;
    private String sender;
    private String receiver;
    private String content;
    private String conversationId;

    public MonitoringEvent() {}

    public MonitoringEvent(String id, String deviceId, long timestamp, String type, String app, String appName,
                          String sender, String receiver, String content, String conversationId) {
        this.id = id;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.type = type;
        this.app = app;
        this.appName = appName;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.conversationId = conversationId;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
