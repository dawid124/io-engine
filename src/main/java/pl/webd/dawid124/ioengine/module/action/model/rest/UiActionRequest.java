package pl.webd.dawid124.ioengine.module.action.model.rest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.webd.dawid124.ioengine.module.action.model.server.ILedChangeData;
import pl.webd.dawid124.ioengine.module.action.model.server.LedChangeData;

import java.io.Serializable;
import java.util.List;

public class UiActionRequest implements Serializable {

    private String zoneId;
    private String sceneId;
    private List<IUiAction> ioActions;

    private ILedChangeData ledChangeData;

    public UiActionRequest() {
        this.ledChangeData = new LedChangeData();
    }

    public UiActionRequest(String zoneId, String sceneId, List<IUiAction> ioActions) {
        this.zoneId = zoneId;
        this.sceneId = sceneId;
        this.ioActions = ioActions;
        this.ledChangeData = new LedChangeData();
    }

    public UiActionRequest(String zoneId, String sceneId, List<IUiAction> ioActions, ILedChangeData ledChangeData) {
        this.zoneId = zoneId;
        this.sceneId = sceneId;
        this.ioActions = ioActions;
        this.ledChangeData = ledChangeData;
    }

    public List<IUiAction> getActions() {
        return ioActions;
    }

    public void setActions(List<IUiAction> ioActions) {
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

    public ILedChangeData getLedChangeData() {
        return ledChangeData;
    }

    public void setLedChangeData(ILedChangeData ledChangeData) {
        this.ledChangeData = ledChangeData;
    }
}
