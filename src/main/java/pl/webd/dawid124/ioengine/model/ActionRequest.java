package pl.webd.dawid124.ioengine.model;

import java.io.Serializable;
import java.util.List;

public class ActionRequest implements Serializable {

    private List<Action> actions;

    public ActionRequest() {}

    public ActionRequest(List<Action> actions) {
        this.actions = actions;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
