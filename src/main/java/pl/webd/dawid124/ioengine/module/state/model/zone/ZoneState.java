package pl.webd.dawid124.ioengine.module.state.model.zone;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.structure.model.TemperatureScenes;

import java.util.HashMap;
import java.util.Map;

@Document(collection = "zone-state", schemaVersion= "1.0")
public class ZoneState {

    @Id
    private String id;
    private String name;

    private String activeScene;
    private String activeTemperatureScene;
    private Map<String, SceneState> sceneStates;
    private Map<String, TemperatureScenes> temperatureSceneStates;
    private Map<String, IVariable> variables;

    public ZoneState() {}

    public ZoneState(String id, String name, String activeScene, String activeTemperatureScene) {
        this.id = id;
        this.name = name;
        this.activeScene = activeScene;
        this.activeTemperatureScene = activeTemperatureScene;
        this.variables = new HashMap<>();
        this.sceneStates = new HashMap<>();
        this.temperatureSceneStates = new HashMap<>();
    }

    public void addSceneState(SceneState state) {
        sceneStates.put(state.getId(), state);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setVariables(Map<String, IVariable> variables) {
        this.variables = variables;
    }

    public Map<String, IVariable> getVariables() {
        return variables;
    }

    public String getActiveScene() {
        return activeScene;
    }

    public SceneState findActiveScene() {
        return sceneStates.get(activeScene);
    }

    public void setActiveScene(String activeScene) {
        this.activeScene = activeScene;
    }

    public Map<String, SceneState> getSceneStates() {
        return sceneStates;
    }

    public void setSceneStates(Map<String, SceneState> sceneStates) {
        this.sceneStates = sceneStates;
    }

    public void setTemperatureSceneStates(Map<String, TemperatureScenes> temperatureSceneStates) {
        this.temperatureSceneStates = temperatureSceneStates;
    }

    public Map<String, TemperatureScenes> getTemperatureSceneStates() {
        return temperatureSceneStates;
    }

    public String getActiveTemperatureScene() {
        return activeTemperatureScene;
    }

    public void setActiveTemperatureScene(String activeTemperatureScene) {
        this.activeTemperatureScene = activeTemperatureScene;
    }
}
