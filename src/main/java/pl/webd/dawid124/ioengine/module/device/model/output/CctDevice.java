package pl.webd.dawid124.ioengine.module.device.model.output;

import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.module.state.model.device.ColorLedDeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;

public class CctDevice extends Device {

    private final int pinWW;
    private final int pinCW;

    public CctDevice(String id, String name, IDriverConfiguration driverConfiguration, int pinWW, int pinCW) {
        super(id, name, driverConfiguration);

        this.pinWW = pinWW;
        this.pinCW = pinCW;
    }

    @Override public EDeviceType getIoType() {
        return EDeviceType.CCT;
    }

    @Override public DeviceState getInitialState() {
        return new ColorLedDeviceState(id, name);
    }

    public int getPinWW() {
        return pinWW;
    }

    public int getPinCW() {
        return pinCW;
    }
}
