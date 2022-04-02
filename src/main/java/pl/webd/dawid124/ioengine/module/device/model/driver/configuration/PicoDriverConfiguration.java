package pl.webd.dawid124.ioengine.module.device.model.driver.configuration;

import pl.webd.dawid124.ioengine.module.device.model.driver.config.PicoDriverConfig;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.PicoDriver;

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
