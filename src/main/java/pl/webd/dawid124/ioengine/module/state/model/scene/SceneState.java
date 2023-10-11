package pl.webd.dawid124.ioengine.module.state.model.scene;

import org.apache.commons.lang3.SerializationUtils;
import pl.webd.dawid124.ioengine.module.state.model.device.GroupState;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class SceneState {

    private String id;
    private String name;

    private LinkedHashMap<String, GroupState> groupState;

    public SceneState() {}

    public SceneState(String id, String name) {
        this.id = id;
        this.name = name;
        this.groupState = new LinkedHashMap<>();
    }

    public SceneState(String id, String name, LinkedHashMap<String, GroupState> groupState, Map<String, SceneState> sceneStates) {
        this.id = id;
        this.name = name;
        this.groupState = groupState;
    }

    public SceneState(String id, String name, LinkedHashMap<String, GroupState> groupState) {
        this.id = id;
        this.name = name;
        this.groupState = groupState;
    }

    public void addGroupState(GroupState state) {
        GroupState copyOfState = SerializationUtils.clone(state);
        groupState.put(state.getState().getIoId(), copyOfState);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setGroupState(LinkedHashMap<String, GroupState> groupState) {
        this.groupState = groupState;
    }

    public LinkedHashMap<String, GroupState> getGroupState() {
        return groupState;
    }


    public Optional<GroupState> findStateById(String ioId) {
        for (GroupState state: groupState.values()) {
            GroupState found = findStateById(state, ioId);
            if (found != null) return Optional.of(found);
        }

        return Optional.empty();
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
