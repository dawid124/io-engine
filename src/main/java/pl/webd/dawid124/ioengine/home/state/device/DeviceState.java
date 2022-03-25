package pl.webd.dawid124.ioengine.home.state.device;

public abstract class DeviceState {

    private final String id;
    private final String name;

    protected DeviceState(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
