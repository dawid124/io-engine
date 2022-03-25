package pl.webd.dawid124.ioengine.home.devices.driver.config;

public class PicoDriverConfig implements IDriverConfig {

    private final EIoDriverLocation location;

    public PicoDriverConfig(EIoDriverLocation location) {
        this.location = location;
    }

    public EIoDriverLocation getLocation() {
        return location;
    }
}
