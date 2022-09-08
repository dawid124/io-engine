package pl.webd.dawid124.ioengine.module.state.model.device;

import java.io.Serializable;

public abstract class DeviceState implements Cloneable, Serializable {

    private String ioId;
    private String name;
    private EDeviceStateType ioType;

    protected DeviceState() {}

    protected DeviceState(String ioId, String name, EDeviceStateType ioType) {
        this.ioId = ioId;
        this.name = name;
        this.ioType = ioType;
    }

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
