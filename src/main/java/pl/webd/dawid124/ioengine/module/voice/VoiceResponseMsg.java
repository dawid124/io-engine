package pl.webd.dawid124.ioengine.module.voice;

public class VoiceResponseMsg {
    private final String text;
    private final String siteId;
    private final int volume;

    public VoiceResponseMsg(String text, String siteId) {
        this.text = text;
        this.siteId = siteId;
        this.volume = 1;
    }

    public VoiceResponseMsg(String text, String siteId, int volume) {
        this.text = text;
        this.siteId = siteId;
        this.volume = volume;
    }

    public String getText() {
        return text;
    }

    public String getSiteId() {
        return siteId;
    }

    public int getVolume() {
        return volume;
    }
}
