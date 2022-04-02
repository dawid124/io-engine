package pl.webd.dawid124.ioengine.module.device.model.driver.instance;

public class PicoDriver implements IDriver {

    private final String id;

    public PicoDriver(String id) {
        this.id = id;
    }

    @Override public String getId() {
        return id;
    }

    @Override public EIoDriverType getType() {
        return EIoDriverType.MQTT;
    }
}
