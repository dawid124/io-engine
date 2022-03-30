package pl.webd.dawid124.ioengine.model;

import java.io.Serializable;
import java.util.List;

public class ActionRequest implements Serializable {

    private String zoneId;

    private List<IoAction> ioActions;

    public ActionRequest() {}

    public ActionRequest(List<IoAction> ioActions) {
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
}
