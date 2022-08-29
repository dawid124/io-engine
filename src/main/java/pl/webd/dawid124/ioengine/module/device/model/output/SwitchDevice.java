package pl.webd.dawid124.ioengine.module.device.model.output;

import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.EDeviceStateType;
import pl.webd.dawid124.ioengine.module.state.model.device.SwitchDeviceState;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfig;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfigSwitch;

public class SwitchDevice extends Device {

    private final int pin;
    private final ESwitchType type;

    public SwitchDevice(String id, String name, IDriverConfiguration driverConfiguration, int pin, ESwitchType type) {
        super(id, name, driverConfiguration);
        this.pin = pin;
        this.type = type;
    }

    @Override
    public EDeviceType getIoType() {
        return EDeviceType.SWITCH;
    }

    @Override
    public DeviceState getInitialState() {
        return new SwitchDeviceState(id, name, EDeviceStateType.SWITCH, false);
    }

    @Override
    public IoConfig toIoConfig() {
        String location = getDriverConfiguration().getConfig().getLocation().toString();
        return new IoConfigSwitch(id, getIoType(), location, pin, type);
    }
}
