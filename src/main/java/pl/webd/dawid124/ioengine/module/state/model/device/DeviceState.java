package pl.webd.dawid124.ioengine.module.state.model.device;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import pl.webd.dawid124.ioengine.module.device.model.adapter.DeviceStateJsonDeserializer;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;

import java.io.Serializable;

@Document(collection = "device-state", schemaVersion= "1.0")
@JsonDeserialize(using = DeviceStateJsonDeserializer.class)
public abstract class DeviceState implements Cloneable, Serializable {

    @Id
    private String ioId;
    private String name;
    private EDeviceStateType ioType;

    public DeviceState() {}

    protected DeviceState(String ioId, String name, EDeviceStateType ioType) {
        this.ioId = ioId;
        this.name = name;
        this.ioType = ioType;
    }

    public void update(IoAction a) {};

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
