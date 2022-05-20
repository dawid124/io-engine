package pl.webd.dawid124.ioengine.module.device.model.driver.config;

public class LocalDriverConfig implements IDriverConfig<ELocalDriverLocation> {

    private final ELocalDriverLocation location;

    public LocalDriverConfig(ELocalDriverLocation location) {
        this.location = location;
    }

    public ELocalDriverLocation getLocation() {
        return location;
    }
}
