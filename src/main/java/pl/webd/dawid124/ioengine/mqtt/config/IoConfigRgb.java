package pl.webd.dawid124.ioengine.mqtt.config;

import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;

public class IoConfigRgb extends IoConfig {

    private final String location;

    private final int pinR;
    private final int pinG;
    private final int pinB;
    private final int pinW;
    private final int pinWW;

    public IoConfigRgb(String id, EDeviceType ioType,
                       String location, int pinR, int pinG, int pinB, int pinW) {
        super(id, ioType);
        this.location = location;
        this.pinR = pinR;
        this.pinG = pinG;
        this.pinB = pinB;
        this.pinW = pinW;
        this.pinWW = -1;
    }

    public IoConfigRgb(String id, EDeviceType ioType,
                       String location, int pinR, int pinG, int pinB, int pinW, int pinWW) {
        super(id, ioType);
        this.location = location;
        this.pinR = pinR;
        this.pinG = pinG;
        this.pinB = pinB;
        this.pinW = pinW;
        this.pinWW = pinWW;
    }

    public String getLocation() {
        return location;
    }

    public int getPinR() {
        return pinR;
    }

    public int getPinG() {
        return pinG;
    }

    public int getPinB() {
        return pinB;
    }

    public int getPinW() {
        return pinW;
    }
}
