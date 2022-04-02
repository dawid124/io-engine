package pl.webd.dawid124.ioengine.module.state.model.device;

import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;

public class LedDeviceState extends DeviceState {

    protected int brightness;

    public LedDeviceState(String id, String name) {
        super(id, name, EDeviceStateType.LED);
        this.brightness = 0;
    }

    public LedDeviceState(String id, String name, EDeviceStateType stateType) {
        super(id, name, stateType);
        this.brightness = 0;
    }

    @Override
    public UiAction toAction() {
        UiAction action = new UiAction();
        return action;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }
}
