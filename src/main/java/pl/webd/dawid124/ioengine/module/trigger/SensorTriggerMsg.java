package pl.webd.dawid124.ioengine.module.trigger;

public class SensorTriggerMsg {

    private String id;

    private boolean state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
