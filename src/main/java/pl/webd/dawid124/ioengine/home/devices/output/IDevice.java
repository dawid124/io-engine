package pl.webd.dawid124.ioengine.home.devices.output;

import pl.webd.dawid124.ioengine.home.devices.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.home.state.device.DeviceState;

public interface IDevice {

    IDriverConfiguration getDriverConfiguration();

    EDeviceType getIoType();

    String getId();

    String getName();

    DeviceState getInitialState();
}
