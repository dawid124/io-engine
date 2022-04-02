package pl.webd.dawid124.ioengine.module.state.model.device;

import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;

public abstract class DeviceState implements Cloneable {

    private String ioId;
    private String name;
    private EDeviceStateType ioType;

    protected DeviceState() {}

    protected DeviceState(String ioId, String name, EDeviceStateType ioType) {
        this.ioId = ioId;
        this.name = name;
        this.ioType = ioType;
    }

    public abstract UiAction toAction();

    public String getIoId() {
        return ioId;
    }

    public EDeviceStateType getIoType() {
        return ioType;
    }

    public String getName() {
        return name;
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
