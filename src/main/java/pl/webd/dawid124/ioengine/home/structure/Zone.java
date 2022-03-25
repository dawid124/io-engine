package pl.webd.dawid124.ioengine.home.structure;

import pl.webd.dawid124.ioengine.home.devices.output.IDevice;
import pl.webd.dawid124.ioengine.home.state.device.DeviceState;
import pl.webd.dawid124.ioengine.home.state.variable.IVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Zone {

    private final String id;
    private final String name;

    private final ArrayList<String> deviceIds;
    private Map<String, IDevice> devices;

    private Map<String, IVariable> variables;
    private Map<String, DeviceState> deviceStates;

    private final Map<String, Scene> scenes;

    public Zone(String id, String name) {
        this.id = id;
        this.name = name;

        this.deviceIds = new ArrayList<>();
        this.devices = new HashMap<>();
        this.variables = new HashMap<>();
        this.deviceStates = new HashMap<>();
        this.scenes = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public Map<String, IDevice> getDevices() {
        return devices;
    }

    public Map<String, IVariable> getVariables() {
        return variables;
    }

    public Map<String, DeviceState> getDeviceStates() {
        return deviceStates;
    }

    public Map<String, Scene> getScenes() {
        return scenes;
    }

    public void addScene(Scene scene) {
        scenes.put(scene.getId(), scene);
    }

    public void setDevices(Map<String, IDevice> devices) {
        this.devices = devices;
    }

    public void setVariables(Map<String, IVariable> variables) {
        this.variables = variables;
    }

    public void setDeviceStates(Map<String, DeviceState> deviceStates) {
        this.deviceStates = deviceStates;
    }
}
