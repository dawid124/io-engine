package pl.webd.dawid124.ioengine.model;

import pl.webd.dawid124.ioengine.home.state.device.DeviceState;

import java.io.Serializable;
import java.util.Map;

public class ZoneInitResponse implements Serializable {

    private String id;

    private String activeScene;

    private Map<String, DeviceState> deviceState;

    private Map<String, DeviceState> groupState;

    public ZoneInitResponse() {}

    public ZoneInitResponse(String id, String activeScene, Map<String, DeviceState> deviceState, Map<String, DeviceState> groupState) {
        this.id = id;
        this.activeScene = activeScene;
        this.deviceState = deviceState;
        this.groupState = groupState;
    }

    public String getActiveScene() {
        return activeScene;
    }

    public void setActiveScene(String activeScene) {
        this.activeScene = activeScene;
    }

    public Map<String, DeviceState> getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(Map<String, DeviceState> deviceState) {
        this.deviceState = deviceState;
    }

    public Map<String, DeviceState> getGroupState() {
        return groupState;
    }

    public void setGroupState(Map<String, DeviceState> groupState) {
        this.groupState = groupState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
