package pl.webd.dawid124.ioengine.module.structure.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.MotionSensorState;

import java.util.*;

public class Zone {

    private final String id;
    private final String name;

    private final Set<BlindStructure> blinds;
    private final Map<String, Scene> scenes;
    private final Map<String, DeviceState> sensors;
    private final Temperature temperature;

    @JsonIgnore
    private final List<String> deviceIds;


    public Zone(String id, String name, Temperature temperature) {
        this.id = id;
        this.name = name;
        this.temperature = temperature;

        this.deviceIds = new ArrayList<>();
        this.blinds = new TreeSet<>();
        this.scenes = new HashMap<>();
        this.sensors = new HashMap<>();
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

    public Map<String, Scene> getScenes() {
        return scenes;
    }

    public Set<BlindStructure> getBlinds() {
        return blinds;
    }

    public void addScene(Scene scene) {
        scenes.put(scene.getId(), scene);
    }

    public Map<String, DeviceState> getSensors() {
        return sensors;
    }

    public Temperature getTemperature() {
        return temperature;
    }
}
