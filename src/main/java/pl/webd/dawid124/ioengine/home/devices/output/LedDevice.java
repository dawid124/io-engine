package pl.webd.dawid124.ioengine.home.devices.output;

import pl.webd.dawid124.ioengine.home.devices.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.home.state.device.DeviceState;
import pl.webd.dawid124.ioengine.home.state.device.LedDeviceState;

public class LedDevice extends Device {

    private final int pin;

    protected LedDevice(String id, String name, IDriverConfiguration driverConfiguration, int pin) {
        super(id, name, driverConfiguration);

        this.pin = pin;
    }

    @Override public EDeviceType getType() {
        return EDeviceType.LED;
    }

    @Override public DeviceState getInitialState() {
        return new LedDeviceState(id, name);
    }

    public int getPin() {
        return pin;
    }
}
