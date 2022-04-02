package pl.webd.dawid124.ioengine.module.device.model.driver.configuration;

import pl.webd.dawid124.ioengine.module.device.model.driver.config.IDriverConfig;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.IDriver;

public interface IDriverConfiguration {

    IDriver getDriver();

    IDriverConfig getConfig();

}
