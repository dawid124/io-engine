package pl.webd.dawid124.ioengine.mqtt.config;

import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;

public class IoConfigMotionSensor extends IoConfig {

    private final int pin;

    public IoConfigMotionSensor(String id, EDeviceType ioType, int pin) {
        super(id, ioType);
        this.pin = pin;
    }

    public int getPin() {
        return pin;
    }
}
