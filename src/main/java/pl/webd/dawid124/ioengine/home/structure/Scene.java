package pl.webd.dawid124.ioengine.home.structure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.webd.dawid124.ioengine.home.state.device.DeviceState;
import pl.webd.dawid124.ioengine.home.structure.group.LightGroup;

import java.util.List;

public class Scene {

    private final String id;
    private final String name;
    private final int order;

    private List<LightGroup> groups;

    @JsonIgnore
    private List<DeviceState> lightsState;

    public Scene(String id, String name, int order) {
        this.id = id;
        this.name = name;
        this.order = order;
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

    public List<LightGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<LightGroup> groups) {
        this.groups = groups;
    }

    public List<DeviceState> getLightsState() {
        return lightsState;
    }

    public void setLightsState(List<DeviceState> lightsState) {
        this.lightsState = lightsState;
    }
}
