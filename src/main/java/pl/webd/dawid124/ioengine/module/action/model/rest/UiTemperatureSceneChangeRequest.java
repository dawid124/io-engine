package pl.webd.dawid124.ioengine.module.action.model.rest;

public class UiTemperatureSceneChangeRequest {

    private String zoneId;
    private String sceneId;

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }
}
