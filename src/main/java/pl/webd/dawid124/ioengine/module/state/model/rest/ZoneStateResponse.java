package pl.webd.dawid124.ioengine.module.state.model.rest;

import pl.webd.dawid124.ioengine.module.state.model.device.GroupState;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.structure.model.TemperatureScenes;

import java.io.Serializable;
import java.util.Map;

public class ZoneStateResponse implements Serializable {

    private String id;

    private String activeScene;
    private String activeTemperatureScene;

    private Map<String, IVariable> variables;

    private Map<String, GroupState> groupState;
    private Map<String, TemperatureScenes> temperatureState;

    public ZoneStateResponse() {}

    public ZoneStateResponse(
            String id, String activeScene, String activeTemperatureScene,
            Map<String, GroupState> groupState,
            Map<String, TemperatureScenes> temperatureState,
            Map<String, IVariable> variables) {

        this.id = id;
        this.activeTemperatureScene = activeTemperatureScene;
        this.activeScene = activeScene;
        this.groupState = groupState;
        this.temperatureState = temperatureState;
        this.variables = variables;
    }

    public String getActiveScene() {
        return activeScene;
    }

    public void setActiveScene(String activeScene) {
        this.activeScene = activeScene;
    }

    public Map<String, GroupState> getGroupState() {
        return groupState;
    }

    public void setGroupState(Map<String, GroupState> groupState) {
        this.groupState = groupState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, IVariable> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, IVariable> variables) {
        this.variables = variables;
    }

    public String getActiveTemperatureScene() {
        return activeTemperatureScene;
    }

    public void setActiveTemperatureScene(String activeTemperatureScene) {
        this.activeTemperatureScene = activeTemperatureScene;
    }

    public Map<String, TemperatureScenes> getTemperatureState() {
        return temperatureState;
    }

    public void setTemperatureState(Map<String, TemperatureScenes> temperatureState) {
        this.temperatureState = temperatureState;
    }
}
