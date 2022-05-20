package pl.webd.dawid124.ioengine.mqtt.config;

import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;

public class IoConfig {

    private final String id;
    private final EDeviceType ioType;

    protected IoConfig(String id, EDeviceType ioType) {
        this.id = id;
        this.ioType = ioType;
    }

    public String getId() {
        return id;
    }

    public EDeviceType getIoType() {
        return ioType;
    }
}
