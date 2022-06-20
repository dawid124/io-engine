package pl.webd.dawid124.ioengine.module.device.model.output;

import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.IDriverConfiguration;

public class RgbwDevice extends RgbwwDevice {

    public RgbwDevice(String id, String name, IDriverConfiguration driverConfiguration, int pinR, int pinG, int pinB, int pinW) {
        super(id, name, driverConfiguration, pinR, pinG, pinB, pinW, -1);
    }
}
