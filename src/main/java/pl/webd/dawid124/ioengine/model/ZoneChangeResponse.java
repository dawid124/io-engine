package pl.webd.dawid124.ioengine.model;

public class ZoneChangeResponse {

    private String zoneId;
    private String sceneId;

    public ZoneChangeResponse(String zoneId, String sceneId) {
        this.zoneId = zoneId;
        this.sceneId = sceneId;
    }

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
