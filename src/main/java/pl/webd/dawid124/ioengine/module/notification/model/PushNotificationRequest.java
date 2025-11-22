package pl.webd.dawid124.ioengine.module.notification.model;

import java.util.Map;

public class PushNotificationRequest {
    private String token;
    private String title;
    private String body;
    private Map<String, String> data;

    public PushNotificationRequest() {
    }

    public PushNotificationRequest(String token, String title, String body) {
        this.token = token;
        this.title = title;
        this.body = body;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
