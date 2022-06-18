package pl.webd.dawid124.ioengine.module.state.model.rest;

import pl.webd.dawid124.ioengine.module.state.model.device.GroupState;

import java.io.Serializable;
import java.util.Map;

public class ZoneStateResponse implements Serializable {

    private String id;

    private String activeScene;

    private Map<String, GroupState> groupState;

    public ZoneStateResponse() {}

    public ZoneStateResponse(String id, String activeScene, Map<String, GroupState> groupState) {
        this.id = id;
        this.activeScene = activeScene;
        this.groupState = groupState;
    }

    public String getActiveScene() {
        return activeScene;
    }

    public void setActiveScene(String activeScene) {
        this.activeScene = activeScene;
    }

    public Map<String, GroupState> getGroupState() {
        return groupState;
    }

    public void setGroupState(Map<String, GroupState> groupState) {
        this.groupState = groupState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
