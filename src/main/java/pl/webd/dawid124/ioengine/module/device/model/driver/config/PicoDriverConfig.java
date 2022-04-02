package pl.webd.dawid124.ioengine.module.device.model.driver.config;

public class PicoDriverConfig implements IDriverConfig {

    private final EPicoDriverLocation location;

    public PicoDriverConfig(EPicoDriverLocation location) {
        this.location = location;
    }

    public EPicoDriverLocation getLocation() {
        return location;
    }
}
