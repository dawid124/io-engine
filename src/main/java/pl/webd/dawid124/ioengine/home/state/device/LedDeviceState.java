package pl.webd.dawid124.ioengine.home.state.device;

import pl.webd.dawid124.ioengine.model.IoAction;

public class LedDeviceState extends DeviceState {

    protected int brightness;

    public LedDeviceState(String id) {
        super(id, EDeviceStateType.LED);
        this.brightness = 0;
    }

    public LedDeviceState(String id, EDeviceStateType stateType) {
        super(id, stateType);
        this.brightness = 0;
    }

    @Override
    public IoAction toAction() {
        IoAction action = new IoAction();
        return action;
    }

    public LedDeviceState(String id, int brightness) {
        super(id, EDeviceStateType.LED);
        this.brightness = brightness;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }
}
