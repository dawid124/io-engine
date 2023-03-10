package pl.webd.dawid124.ioengine.module.voice;

import pl.webd.dawid124.ioengine.module.voice.dto.Speech;

public class VoiceResponse {
    private final Speech speech;

    public VoiceResponse(Speech speech) {
        this.speech = speech;
    }

    public static VoiceResponse text(String text) {
        return new VoiceResponse(new Speech(text));
    }

    public Speech getSpeech() {
        return speech;
    }
}
