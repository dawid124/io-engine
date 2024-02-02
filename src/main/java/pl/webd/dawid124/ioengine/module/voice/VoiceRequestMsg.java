package pl.webd.dawid124.ioengine.module.voice;

import pl.webd.dawid124.ioengine.module.voice.mqttdto.Intent;
import pl.webd.dawid124.ioengine.module.voice.mqttdto.Slot;

import java.util.List;

public class VoiceRequestMsg {

    private Intent intent;
    private String siteId;
    private List<Slot> slots;

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }
}
