package pl.webd.dawid124.ioengine.home.state.device;

public abstract class DeviceState implements Cloneable {

    private String ioId;
    private String name;
    private EDeviceStateType type;

    public DeviceState() {}

    protected DeviceState(String ioId, String name, EDeviceStateType type) {
        this.ioId = ioId;
        this.name = name;
        this.type = type;
    }

    public String getIoId() {
        return ioId;
    }

    public String getName() {
        return name;
    }

    public EDeviceStateType getType() {
        return type;
    }

    @Override
    public DeviceState clone() {
        try {
            return (DeviceState) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
