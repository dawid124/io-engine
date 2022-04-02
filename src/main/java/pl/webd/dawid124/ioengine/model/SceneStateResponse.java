package pl.webd.dawid124.ioengine.model;

import pl.webd.dawid124.ioengine.home.state.scene.SceneState;

public class SceneStateResponse extends SceneState {

    private String zoneId;

    public SceneStateResponse(String zoneId, SceneState scene) {
        super(scene.getId(), scene.getName(), scene.getDeviceState(), scene.getGroupState());

        this.zoneId = zoneId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
