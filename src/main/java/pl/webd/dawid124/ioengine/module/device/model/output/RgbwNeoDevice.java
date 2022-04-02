package pl.webd.dawid124.ioengine.module.device.model.output;

import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.NeoDeviceState;

public class RgbwNeoDevice extends Device {

    private final int pin;

    public RgbwNeoDevice(String id, String name, IDriverConfiguration driverConfiguration, int pin) {
        super(id, name, driverConfiguration);
        this.pin = pin;
    }

    @Override public EDeviceType getIoType() {
        return EDeviceType.NEO;
    }

    @Override public DeviceState getInitialState() {
        return new NeoDeviceState(id, name);
    }

    public int getPin() {
        return pin;
    }
}
