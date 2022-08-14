package pl.webd.dawid124.ioengine.module.device.model.output;

import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.module.state.model.device.ColorLedDeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.LedDeviceState;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfig;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfigRgb;

public class SingleColorLedDevice extends RgbwDevice {
    public SingleColorLedDevice(String id, String name, IDriverConfiguration driverConfiguration, int pin) {
        super(id, name, driverConfiguration, -1, -1, -1, pin);
    }

    @Override public DeviceState getInitialState() {
        return new ColorLedDeviceState(id, name);
    }

}
