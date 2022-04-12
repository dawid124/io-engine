package pl.webd.dawid124.ioengine.module.device.model.output;

import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.module.state.model.device.ColorLedDeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;

public class RgbwwDevice extends Device {

    private final int pinR;
    private final int pinG;
    private final int pinB;
    private final int pinW;
    private final int pinWw;

    public RgbwwDevice(String id, String name, IDriverConfiguration driverConfiguration, int pinR, int pinG, int pinB, int pinW, int pinWw) {
        super(id, name, driverConfiguration);

        this.pinR = pinR;
        this.pinG = pinG;
        this.pinB = pinB;
        this.pinW = pinW;
        this.pinWw = pinWw;
    }

    @Override public EDeviceType getIoType() {
        return EDeviceType.RGBW;
    }

    @Override public DeviceState getInitialState() {
        return new ColorLedDeviceState(id, name);
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

    public int getPinWw() {
        return pinWw;
    }
}
