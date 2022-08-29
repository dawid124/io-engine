package pl.webd.dawid124.ioengine.module.state.model.device;

public class SwitchDeviceState extends DeviceState {

    private boolean on;

    public SwitchDeviceState(String ioId, String name, EDeviceStateType ioType, boolean on) {
        super(ioId, name, ioType);
        this.on = on;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
