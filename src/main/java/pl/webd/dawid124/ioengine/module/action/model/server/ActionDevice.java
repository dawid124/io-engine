package pl.webd.dawid124.ioengine.module.action.model.server;

import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;

public class ActionDevice {

    private final UiAction ioAction;

    private final IDevice device;

    public ActionDevice(UiAction ioAction, IDevice device) {
        this.ioAction = ioAction;
        this.device = device;
    }

    public UiAction getIoAction() {
        return ioAction;
    }

    public IDevice getDevice() {
        return device;
    }
}
