package pl.webd.dawid124.ioengine.module.action.model.server;

import pl.webd.dawid124.ioengine.module.action.model.rest.IUiAction;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;

public class ActionDevice {

    private final IUiAction ioAction;

    private final IDevice device;

    public ActionDevice(IUiAction ioAction, IDevice device) {
        this.ioAction = ioAction;
        this.device = device;
    }

    public IUiAction getIoAction() {
        return ioAction;
    }

    public IDevice getDevice() {
        return device;
    }
}
