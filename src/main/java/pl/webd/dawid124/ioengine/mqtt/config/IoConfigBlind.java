package pl.webd.dawid124.ioengine.mqtt.config;

import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;

public class IoConfigBlind extends IoConfig {

    private final String location;

    private final int pinUp;
    private final int pinDown;

    public IoConfigBlind(String id, EDeviceType ioType, String location, int pinUp, int pinDown) {
        super(id, ioType);
        this.location = location;
        this.pinUp = pinUp;
        this.pinDown = pinDown;
    }

    public String getLocation() {
        return location;
    }

    public int getPinUp() {
        return pinUp;
    }

    public int getPinDown() {
        return pinDown;
    }
}
