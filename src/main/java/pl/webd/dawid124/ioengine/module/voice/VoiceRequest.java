package pl.webd.dawid124.ioengine.module.voice;

import pl.webd.dawid124.ioengine.module.voice.dto.Entities;
import pl.webd.dawid124.ioengine.module.voice.dto.Intent;

import java.util.List;

public class VoiceRequest {

    private Intent intent;

    private List<Entities> entities;

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public List<Entities> getEntities() {
        return entities;
    }

    public void setEntities(List<Entities> entities) {
        this.entities = entities;
    }
}
