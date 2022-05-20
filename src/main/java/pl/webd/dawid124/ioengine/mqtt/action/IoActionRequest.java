package pl.webd.dawid124.ioengine.mqtt.action;

import java.util.List;

public class IoActionRequest {

    private List<IoAction> actions;

    public IoActionRequest() {}

    public IoActionRequest(List<IoAction> actions) {
        this.actions = actions;
    }

    public List<IoAction> getActions() {
        return actions;
    }

    public void setActions(List<IoAction> actions) {
        this.actions = actions;
    }
}
