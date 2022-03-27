package pl.webd.dawid124.ioengine.home.devices.output;

import java.io.Serializable;

public enum EDeviceType implements Serializable {

    BLIND("Blind"),
    NEO("NEO"),
    RGBW("RGBW"),
    CCT("CCT"),
    LED("Led");

    final String name;

    EDeviceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
