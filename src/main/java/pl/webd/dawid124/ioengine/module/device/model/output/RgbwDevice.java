package pl.webd.dawid124.ioengine.module.device.model.output;

import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.ColorLedDeviceState;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfig;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfigRgb;

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

    @Override public EDeviceType getIoType() {
        return EDeviceType.RGBW;
    }

    @Override public DeviceState getInitialState() {
        return new ColorLedDeviceState(id, name);
    }

    @Override
    public IoConfig toIoConfig() {
        String location = getDriverConfiguration().getConfig().getLocation().toString();
        return new IoConfigRgb(id, getIoType(), location, pinR, pinG, pinB, pinW);
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
