package pl.webd.dawid124.ioengine.module.voice.mqttdto;

public class Slot {
    private String entity;
    private Value value;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
