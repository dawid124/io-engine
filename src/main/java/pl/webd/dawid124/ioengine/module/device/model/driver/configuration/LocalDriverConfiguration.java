package pl.webd.dawid124.ioengine.module.device.model.driver.configuration;

import pl.webd.dawid124.ioengine.module.device.model.driver.config.LocalDriverConfig;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.LocalDriver;

public class LocalDriverConfiguration implements IDriverConfiguration {

    private final LocalDriver driver;

    private final LocalDriverConfig config;

    public LocalDriverConfiguration(LocalDriver driver, LocalDriverConfig config) {
        this.driver = driver;
        this.config = config;
    }

    @Override public LocalDriver getDriver() {
        return driver;
    }

    @Override public LocalDriverConfig getConfig() {
        return config;
    }
}
