package pl.webd.dawid124.ioengine.module.state.model.scene;

import pl.webd.dawid124.ioengine.module.state.model.device.GroupState;

import java.util.HashMap;
import java.util.Map;

public class SceneState {

    private final String id;
    private final String name;

    private Map<String, GroupState> groupState;
    private Map<String, SceneState> sceneStates;

    public SceneState(String id, String name) {
        this.id = id;
        this.name = name;
        this.groupState = new HashMap<>();
        this.sceneStates = new HashMap<>();
    }

    public SceneState(String id, String name, Map<String, GroupState> groupState, Map<String, SceneState> sceneStates) {
        this.id = id;
        this.name = name;
        this.groupState = groupState;
        this.sceneStates = sceneStates;
    }

    public SceneState(String id, String name, Map<String, GroupState> groupState) {
        this.id = id;
        this.name = name;
        this.groupState = groupState;
        this.sceneStates = new HashMap<>();
    }

    public void addGroupState(GroupState state) {
        groupState.put(state.getState().getIoId(), state);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setGroupState(Map<String, GroupState> groupState) {
        this.groupState = groupState;
    }

    public Map<String, GroupState> getGroupState() {
        return groupState;
    }

    public Map<String, SceneState> getSceneStates() {
        return sceneStates;
    }

    public GroupState findStateById(String ioId) {
        for (GroupState state: groupState.values()) {
            GroupState found = findStateById(state, ioId);
            if (found != null) return found;
        }

        return null;
    }

    private GroupState findStateById(GroupState parent, String ioId) {
        if (ioId.equals(parent.getState().getIoId())) {
            return parent;
        }

        if (parent.getChildren() == null) {
            return null;
        }

        for (GroupState state: parent.getChildren()) {
            GroupState foundState = findStateById(state, ioId);

            if (foundState != null) {
                return foundState;
            }
        }

        return null;
    }
}
