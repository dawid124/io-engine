package pl.webd.dawid124.ioengine.home.state.device;

public class LedDeviceState extends DeviceState {

    private int brightness;

    public LedDeviceState(String id, String name) {
        super(id, name);
        this.brightness = 0;
    }

    public LedDeviceState(String id, String name, int brightness) {
        super(id, name);
        this.brightness = brightness;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }
}
