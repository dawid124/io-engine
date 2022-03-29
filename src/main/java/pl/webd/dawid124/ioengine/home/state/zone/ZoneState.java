package pl.webd.dawid124.ioengine.home.state.zone;

import pl.webd.dawid124.ioengine.home.state.variable.IVariable;

import java.util.HashMap;
import java.util.Map;

public class ZoneState {

    private final String id;
    private final String name;

    private String activeScene;
    private Map<String, IVariable> variables;

    public ZoneState(String id, String name, String activeScene) {
        this.id = id;
        this.name = name;
        this.activeScene = activeScene;
        this.variables = new HashMap<>();
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

    public void setActiveScene(String activeScene) {
        this.activeScene = activeScene;
    }
}
