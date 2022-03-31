package pl.webd.dawid124.ioengine.home.devices.output;

import pl.webd.dawid124.ioengine.home.devices.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.home.state.device.DeviceState;
import pl.webd.dawid124.ioengine.home.state.device.NeoDeviceState;

public class RgbwNeoDevice extends Device {

    private final int pin;

    public RgbwNeoDevice(String id, String name, IDriverConfiguration driverConfiguration, int pin) {
        super(id, name, driverConfiguration);
        this.pin = pin;
    }

    @Override public EDeviceType getType() {
        return EDeviceType.NEO;
    }

    @Override public DeviceState getInitialState() {
        return new NeoDeviceState(id);
    }

    public int getPin() {
        return pin;
    }
}
