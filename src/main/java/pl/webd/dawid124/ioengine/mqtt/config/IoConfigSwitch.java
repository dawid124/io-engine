package pl.webd.dawid124.ioengine.mqtt.config;

import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;
import pl.webd.dawid124.ioengine.module.device.model.output.ESwitchType;

public class IoConfigSwitch extends IoConfig {

    private final String location;

    private final int pin;
    private final ESwitchType type;

    public IoConfigSwitch(String id, EDeviceType ioType, String location, int pin, ESwitchType type) {
        super(id, ioType);
        this.location = location;
        this.pin = pin;
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public int getPin() {
        return pin;
    }

    public ESwitchType getType() {
        return type;
    }
}
