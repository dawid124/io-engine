package pl.webd.dawid124.ioengine.module.state.model.rest;

import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;

public class SceneStateResponse extends SceneState {

    private String zoneId;

    public SceneStateResponse(String zoneId, SceneState scene) {
        super(scene.getId(), scene.getName(), scene.getGroupState());

        this.zoneId = zoneId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
