package pl.webd.dawid124.ioengine.mqtt.config;

import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;
import pl.webd.dawid124.ioengine.module.device.model.output.ENeoType;

public class IoConfigNeo extends IoConfig {

    private final ENeoType neoType;
    private final boolean reverse;
    private final int pin;
    private final int ledCount;

    public IoConfigNeo(String id, EDeviceType ioType, ENeoType neoType, boolean reverse, int pin, int ledCount) {
        super(id, ioType);
        this.neoType = neoType;
        this.reverse = reverse;
        this.pin = pin;
        this.ledCount = ledCount;
    }

    public ENeoType getNeoType() {
        return neoType;
    }

    public boolean isReverse() {
        return reverse;
    }

    public int getPin() {
        return pin;
    }

    public int getLedCount() {
        return ledCount;
    }
}
