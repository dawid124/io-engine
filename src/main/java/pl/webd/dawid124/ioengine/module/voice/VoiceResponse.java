package pl.webd.dawid124.ioengine.module.voice;

public class VoiceResponse {
    private final String text;

    public VoiceResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
