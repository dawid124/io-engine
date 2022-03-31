package pl.webd.dawid124.ioengine.home.devices.output;

import pl.webd.dawid124.ioengine.home.devices.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.home.state.device.DeviceState;
import pl.webd.dawid124.ioengine.home.state.device.ColorLedDeviceState;

public class RgbwDevice extends Device {

    private final int pinR;
    private final int pinG;
    private final int pinB;
    private final int pinW;

    public RgbwDevice(String id, String name, IDriverConfiguration driverConfiguration, int pinR, int pinG, int pinB, int pinW) {
        super(id, name, driverConfiguration);

        this.pinR = pinR;
        this.pinG = pinG;
        this.pinB = pinB;
        this.pinW = pinW;
    }

    @Override public EDeviceType getType() {
        return EDeviceType.RGBW;
    }

    @Override public DeviceState getInitialState() {
        return new ColorLedDeviceState(id);
    }

    public int getPinR() {
        return pinR;
    }

    public int getPinG() {
        return pinG;
    }

    public int getPinB() {
        return pinB;
    }

    public int getPinW() {
        return pinW;
    }
}
