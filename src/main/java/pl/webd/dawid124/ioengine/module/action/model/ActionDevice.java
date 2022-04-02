package pl.webd.dawid124.ioengine.module.action.model;

import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;

public class ActionDevice {

    private final IoAction ioAction;

    private final IDevice device;

    public ActionDevice(IoAction ioAction, IDevice device) {
        this.ioAction = ioAction;
        this.device = device;
    }

    public IoAction getIoAction() {
        return ioAction;
    }

    public IDevice getDevice() {
        return device;
    }
}
