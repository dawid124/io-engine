package pl.webd.dawid124.ioengine.module.device.model.output;

import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfig;

public interface IDevice {

    IDriverConfiguration getDriverConfiguration();

    EDeviceType getIoType();

    String getId();

    String getName();

    DeviceState getInitialState();

    IoConfig toIoConfig();
}
