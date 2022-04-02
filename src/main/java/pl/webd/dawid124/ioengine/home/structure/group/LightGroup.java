package pl.webd.dawid124.ioengine.home.structure.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.webd.dawid124.ioengine.home.devices.output.EDeviceType;
import pl.webd.dawid124.ioengine.home.state.device.DeviceState;

import java.util.List;

public class LightGroup {

    private String ioId;
    private String name;
    private List<String> deviceIds;
    private EDeviceType type;

    @JsonIgnore
    private DeviceState initialState;

    public String getIoId() {
        return ioId;
    }

    public void setIoId(String ioId) {
        this.ioId = ioId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public EDeviceType getType() {
        return type;
    }

    public void setType(EDeviceType type) {
        this.type = type;
    }

    public DeviceState getInitialState() {
        return initialState;
    }

    public void setInitialState(DeviceState initialState) {
        this.initialState = initialState;
    }
}
