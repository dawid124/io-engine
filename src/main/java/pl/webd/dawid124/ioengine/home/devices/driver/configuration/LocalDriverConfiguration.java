package pl.webd.dawid124.ioengine.home.devices.driver.configuration;

import pl.webd.dawid124.ioengine.home.devices.driver.config.LocalDriverConfig;
import pl.webd.dawid124.ioengine.home.devices.driver.config.PicoDriverConfig;
import pl.webd.dawid124.ioengine.home.devices.driver.instance.LocalDriver;
import pl.webd.dawid124.ioengine.home.devices.driver.instance.PicoDriver;

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
