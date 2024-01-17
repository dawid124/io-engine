package pl.webd.dawid124.ioengine.module.device.model.zigbee;

import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;

public abstract class ZigbeeDevice implements IDevice, ZigbeeApi {

    protected final String id;
    protected final String name;

    protected final IDriverConfiguration driverConfiguration;

    protected ZigbeeDevice(String id, String name, IDriverConfiguration driverConfiguration) {
        this.id = id;
        this.name = name;
        this.driverConfiguration = driverConfiguration;
    }

    @Override public String getId() {
        return id;
    }

    public String getMqttAddress() {
        return id;
    }

    @Override public String getName() {
        return name;
    }

    @Override public IDriverConfiguration getDriverConfiguration() {
        return driverConfiguration;
    }
}
