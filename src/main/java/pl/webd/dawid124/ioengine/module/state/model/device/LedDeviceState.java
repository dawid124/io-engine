package pl.webd.dawid124.ioengine.module.state.model.device;

public class LedDeviceState extends DeviceState {

    protected int brightness;

    public LedDeviceState() {}

    public LedDeviceState(String id, String name) {
        super(id, name, EDeviceStateType.LED);
        this.brightness = 0;
    }

    public LedDeviceState(String id, String name, EDeviceStateType stateType) {
        super(id, name, stateType);
        this.brightness = 0;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }
}
