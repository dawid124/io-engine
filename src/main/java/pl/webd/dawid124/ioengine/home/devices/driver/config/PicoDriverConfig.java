package pl.webd.dawid124.ioengine.home.devices.driver.config;

public class PicoDriverConfig implements IDriverConfig {

    private final EPicoDriverLocation location;

    public PicoDriverConfig(EPicoDriverLocation location) {
        this.location = location;
    }

    public EPicoDriverLocation getLocation() {
        return location;
    }
}
