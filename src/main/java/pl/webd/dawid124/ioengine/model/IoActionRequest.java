package pl.webd.dawid124.ioengine.model;

import java.util.List;

public class IoActionRequest {

    private List<IoAction> ioActions;

    public IoActionRequest() {}

    public IoActionRequest(List<IoAction> ioActions) {
        this.ioActions = ioActions;
    }

    public List<IoAction> getIoActions() {
        return ioActions;
    }

    public void setIoActions(List<IoAction> ioActions) {
        this.ioActions = ioActions;
    }
}
