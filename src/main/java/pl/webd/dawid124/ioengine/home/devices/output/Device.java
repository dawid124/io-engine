package pl.webd.dawid124.ioengine.home.devices.output;

import pl.webd.dawid124.ioengine.home.devices.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.home.state.device.DeviceState;

public abstract class Device implements IDevice {

    protected final String id;
    protected final String name;

    protected final IDriverConfiguration driverConfiguration;

    protected Device(String id, String name, IDriverConfiguration driverConfiguration) {
        this.id = id;
        this.name = name;
        this.driverConfiguration = driverConfiguration;
    }

    @Override public String getId() {
        return id;
    }

    @Override public String getName() {
        return name;
    }

    @Override public IDriverConfiguration getDriverConfiguration() {
        return driverConfiguration;
    }
}
