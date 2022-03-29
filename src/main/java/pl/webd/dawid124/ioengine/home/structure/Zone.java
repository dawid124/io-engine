package pl.webd.dawid124.ioengine.home.structure;

import pl.webd.dawid124.ioengine.home.devices.output.IDevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Zone {

    private final String id;
    private final String name;

    private final int order;

    private final ArrayList<String> deviceIds;
    private Map<String, IDevice> devices;

    private final Map<String, Scene> scenes;

    public Zone(String id, String name, int order) {
        this.id = id;
        this.name = name;
        this.order = order;

        this.deviceIds = new ArrayList<>();
        this.devices = new HashMap<>();
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

    public Map<String, Scene> getScenes() {
        return scenes;
    }

    public int getOrder() {
        return order;
    }

    public void addScene(Scene scene) {
        scenes.put(scene.getId(), scene);
    }

    public void setDevices(Map<String, IDevice> devices) {
        this.devices = devices;
    }
}
