package pl.webd.dawid124.ioengine.module.voice.dto;

public class Session {

    private String sessionId;
    private String siteId;
    private String customData;

    public Session() {}

    public Session(String sessionId, String siteId) {
        this.sessionId = sessionId;
        this.siteId = siteId;
    }

    public Session(Session session) {
        this.sessionId = session.sessionId;
        this.siteId = session.siteId;
        this.customData = session.customData;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getCustomData() {
        return customData;
    }

    public void setCustomData(String customData) {
        this.customData = customData;
    }
}
