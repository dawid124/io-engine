package pl.webd.dawid124.ioengine.module.device.model.output;

import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.NeoDeviceState;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfig;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfigNeo;

public class NeoDevice extends Device {

    private final int pin;
    private final int ledCount;
    private final boolean reverse;
    private final ENeoType neoType;

    public NeoDevice(String id, String name, IDriverConfiguration driverConfiguration, int pin,
                     int ledCount, boolean reverse, ENeoType neoType) {

        super(id, name, driverConfiguration);
        this.pin = pin;
        this.ledCount = ledCount;
        this.reverse = reverse;
        this.neoType = neoType;
    }

    @Override
    public IoConfig toIoConfig() {
        return new IoConfigNeo(id, getIoType(), neoType, reverse, pin, ledCount);
    }

    @Override public EDeviceType getIoType() {
        return EDeviceType.NEO;
    }

    @Override public DeviceState getInitialState() {
        return new NeoDeviceState(id, name);
    }

    public int getPin() {
        return pin;
    }

    public boolean isReverse() {
        return reverse;
    }

    public int getLedCount() {
        return ledCount;
    }

    public ENeoType getNeoType() {
        return neoType;
    }
}
