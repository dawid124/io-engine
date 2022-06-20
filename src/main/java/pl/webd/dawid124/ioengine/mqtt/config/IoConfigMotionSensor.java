package pl.webd.dawid124.ioengine.mqtt.config;

import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;

public class IoConfigMotionSensor extends IoConfig {

    private final int pin;
    private final boolean reverse;

    public IoConfigMotionSensor(String id, EDeviceType ioType, int pin, boolean reverse) {
        super(id, ioType);
        this.pin = pin;
        this.reverse = reverse;
    }

    public int getPin() {
        return pin;
    }

    public boolean isReverse() {
        return reverse;
    }
}
