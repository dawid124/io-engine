package pl.webd.dawid124.ioengine.module.structure.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.collections.transformation.SortedList;
import pl.webd.dawid124.ioengine.module.device.model.input.BlindDevice;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;

import java.util.*;

public class Zone {

    private final String id;
    private final String name;

    private final int order;

    private final Set<BlindStructure> blinds;
    private final Map<String, Scene> scenes;

    @JsonIgnore
    private final List<String> deviceIds;


    public Zone(String id, String name, int order) {
        this.id = id;
        this.name = name;
        this.order = order;

        this.deviceIds = new ArrayList<>();
        this.blinds = new TreeSet<>();
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

    public Map<String, Scene> getScenes() {
        return scenes;
    }

    public Set<BlindStructure> getBlinds() {
        return blinds;
    }

    public int getOrder() {
        return order;
    }

    public void addScene(Scene scene) {
        scenes.put(scene.getId(), scene);
    }
}
