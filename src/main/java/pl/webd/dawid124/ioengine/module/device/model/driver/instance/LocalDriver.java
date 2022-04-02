package pl.webd.dawid124.ioengine.module.device.model.driver.instance;

public class LocalDriver implements IDriver {

    private final String id;

    public LocalDriver(String id) {
        this.id = id;
    }

    @Override public String getId() {
        return id;
    }

    @Override public EIoDriverType getType() {
        return EIoDriverType.LOCAL_IO;
    }
}
