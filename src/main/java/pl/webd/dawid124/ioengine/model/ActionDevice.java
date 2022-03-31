package pl.webd.dawid124.ioengine.model;

import pl.webd.dawid124.ioengine.home.devices.output.IDevice;

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
