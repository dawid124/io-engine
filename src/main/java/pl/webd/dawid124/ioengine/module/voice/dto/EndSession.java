package pl.webd.dawid124.ioengine.module.voice.dto;

public class EndSession extends Session {

    private final String text;

    public EndSession(String sessionId, String siteId, String text) {
        super(sessionId, siteId);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
