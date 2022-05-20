package pl.webd.dawid124.ioengine.module.device.model.output;

import java.io.Serializable;

public enum EDeviceType implements Serializable {

    BLIND("Blind"),
    NEO("RGBW"),
    RGBW("RGBW"),
    CCT("CCT"),
    LED("Led"),
    MOTION_SENSOR("MOTION_SENSOR");

    final String name;

    EDeviceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
