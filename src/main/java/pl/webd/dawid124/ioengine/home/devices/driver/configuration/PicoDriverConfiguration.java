package pl.webd.dawid124.ioengine.home.devices.driver.configuration;

import pl.webd.dawid124.ioengine.home.devices.driver.config.PicoDriverConfig;
import pl.webd.dawid124.ioengine.home.devices.driver.instance.PicoDriver;

public class PicoDriverConfiguration implements IDriverConfiguration {

    private final PicoDriver driver;

    private final PicoDriverConfig config;

    public PicoDriverConfiguration(PicoDriver driver, PicoDriverConfig config) {
        this.driver = driver;
        this.config = config;
    }

    @Override public PicoDriver getDriver() {
        return driver;
    }

    @Override public PicoDriverConfig getConfig() {
        return config;
    }
}
