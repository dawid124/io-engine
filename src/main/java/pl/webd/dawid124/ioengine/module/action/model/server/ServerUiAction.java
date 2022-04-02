package pl.webd.dawid124.ioengine.module.action.model.server;

import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;

public class ServerUiAction extends ServerAction {

    private final UiAction ioAction;

    public ServerUiAction(UiAction ioAction, ServerDevice device) {
        super(device);
        this.ioAction = ioAction;
    }

    public UiAction getIoAction() {
        return ioAction;
    }
}
