package pl.webd.dawid124.ioengine.home.devices.driver.configuration;

import pl.webd.dawid124.ioengine.home.devices.driver.config.IDriverConfig;
import pl.webd.dawid124.ioengine.home.devices.driver.instance.IDriver;

public interface IDriverConfiguration {

    IDriver getDriver();

    IDriverConfig getConfig();

}
