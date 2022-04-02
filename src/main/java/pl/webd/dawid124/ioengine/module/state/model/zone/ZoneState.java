package pl.webd.dawid124.ioengine.module.state.model.zone;

import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.HashMap;
import java.util.Map;

public class ZoneState {

    private final String id;
    private final String name;

    private String activeScene;

    private Map<String, SceneState> sceneStates;
    private Map<String, IVariable> variables;

    public ZoneState(String id, String name, String activeScene) {
        this.id = id;
        this.name = name;
        this.activeScene = activeScene;
        this.variables = new HashMap<>();
        this.sceneStates = new HashMap<>();
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
}
