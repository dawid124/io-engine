package pl.webd.dawid124.ioengine.module.automation.trigger;

public class SensorTriggerMsg {

    private String id;
    private boolean state;
    private String action;

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

    @Override
    public String toString() {
        return "SensorTriggerMsg{" +
                "id='" + id + '\'' +
                ", state=" + state +
                ", action='" + action + '\'' +
                '}';
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
