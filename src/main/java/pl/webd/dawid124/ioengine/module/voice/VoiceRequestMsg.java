package pl.webd.dawid124.ioengine.module.voice;

import pl.webd.dawid124.ioengine.module.voice.dto.Session;
import pl.webd.dawid124.ioengine.module.voice.mqttdto.Intent;
import pl.webd.dawid124.ioengine.module.voice.mqttdto.Slot;

import java.util.List;

public class VoiceRequestMsg extends Session {

    private Intent intent;
    private List<Slot> slots;

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }
}
