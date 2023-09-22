package pl.webd.dawid124.ioengine.module.device.model.driver.configuration;

import pl.webd.dawid124.ioengine.module.device.model.driver.config.IDriverConfig;
import pl.webd.dawid124.ioengine.module.device.model.driver.config.LocalDriverConfig;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.LocalDriver;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.MqttDriver;

public class ZigbeeDriverConfiguration implements IDriverConfiguration {

    private final MqttDriver driver;


    public ZigbeeDriverConfiguration(MqttDriver driver) {
        this.driver = driver;
    }

    @Override public MqttDriver getDriver() {
        return driver;
    }

    @Override public IDriverConfig getConfig() {
        return null;
    }
}
