package pl.webd.dawid124.ioengine.module.structure.model;

import pl.webd.dawid124.ioengine.module.state.model.device.GroupState;

import java.util.*;

public class Scene {

    private final String id;
    private final String name;
    private final int order;

    private final Map<String, Scene> scenes;

    private Set<GroupState> groups;


    public Scene(String id, String name, int order) {
        this.id = id;
        this.name = name;
        this.order = order;
        this.scenes = new HashMap<>();
        this.groups = new HashSet<>();
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

    public Set<GroupState> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupState> groups) {
        this.groups = groups;
    }

    public Map<String, Scene> getScenes() {
        return scenes;
    }
}
