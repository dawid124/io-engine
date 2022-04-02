package pl.webd.dawid124.ioengine.model;

import java.io.Serializable;
import java.util.List;

public class UiActionRequest implements Serializable {

    private String zoneId;
    private String sceneId;

    private List<IoAction> ioActions;

    public UiActionRequest() {}

    public UiActionRequest(String zoneId, String sceneId, List<IoAction> ioActions) {
        this.zoneId = zoneId;
        this.sceneId = sceneId;
        this.ioActions = ioActions;
    }

    public List<IoAction> getActions() {
        return ioActions;
    }

    public void setActions(List<IoAction> ioActions) {
        this.ioActions = ioActions;
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
