package pl.webd.dawid124.ioengine.home.state.scene;

import pl.webd.dawid124.ioengine.home.state.device.DeviceState;

import java.util.HashMap;
import java.util.Map;

public class SceneState {

    private final String id;
    private final String name;

    private Map<String, DeviceState> deviceState;
    private Map<String, DeviceState> groupState;

    public SceneState(String id, String name) {
        this.id = id;
        this.name = name;
        this.groupState = new HashMap<>();
        this.deviceState = new HashMap<>();
    }

    public SceneState(String id, String name, Map<String, DeviceState> deviceState, Map<String, DeviceState> groupState) {
        this.id = id;
        this.name = name;
        this.deviceState = deviceState;
        this.groupState = groupState;
    }

    public void addGroupState(DeviceState state) {
        groupState.put(state.getIoId(), state);
    }

    public void addDeviceState(DeviceState state) {
        deviceState.put(state.getIoId(), state);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setGroupState(Map<String, DeviceState> groupState) {
        this.groupState = groupState;
    }

    public Map<String, DeviceState> getGroupState() {
        return groupState;
    }

    public Map<String, DeviceState> getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(Map<String, DeviceState> deviceState) {
        this.deviceState = deviceState;
    }
}
