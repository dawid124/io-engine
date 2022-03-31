package pl.webd.dawid124.ioengine.home.state.device;

import pl.webd.dawid124.ioengine.model.IoAction;

public abstract class DeviceState implements Cloneable {

    private String ioId;
    private EDeviceStateType type;

    protected DeviceState() {}

    protected DeviceState(String ioId, EDeviceStateType type) {
        this.ioId = ioId;
        this.type = type;
    }

    public abstract IoAction toAction();

    public String getIoId() {
        return ioId;
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
