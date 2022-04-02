package pl.webd.dawid124.ioengine.module.action.model.rest;

import java.io.Serializable;
import java.util.List;

public class UiActionRequest implements Serializable {

    private String zoneId;
    private String sceneId;

    private List<UiAction> ioActions;

    public UiActionRequest() {}

    public UiActionRequest(String zoneId, String sceneId, List<UiAction> ioActions) {
        this.zoneId = zoneId;
        this.sceneId = sceneId;
        this.ioActions = ioActions;
    }

    public List<UiAction> getActions() {
        return ioActions;
    }

    public void setActions(List<UiAction> ioActions) {
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
