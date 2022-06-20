package pl.webd.dawid124.ioengine.module.structure.model;

import pl.webd.dawid124.ioengine.module.state.model.device.GroupState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scene {

    private final String id;
    private final String name;
    private final int order;

    private final Map<String, Scene> scenes;

    private List<GroupState> groups;


    public Scene(String id, String name, int order) {
        this.id = id;
        this.name = name;
        this.order = order;
        this.scenes = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public List<GroupState> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupState> groups) {
        this.groups = groups;
    }

    public Map<String, Scene> getScenes() {
        return scenes;
    }
}
