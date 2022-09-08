package pl.webd.dawid124.ioengine.module.state.model.device;

import java.io.Serializable;
import java.util.List;

public class GroupState implements Serializable {

    private boolean hidden;
    private DeviceState state;
    private List<GroupState> children;

    public GroupState(DeviceState state, List<GroupState> children) {
        this.state = state;
        this.children = children;
    }

    public GroupState(DeviceState state, List<GroupState> children, boolean hidden) {
        this.hidden = hidden;
        this.state = state;
        this.children = children;
    }

    public DeviceState getState() {
        return state;
    }

    public List<GroupState> getChildren() {
        return children;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setState(DeviceState state) {
        this.state = state;
    }

    public void setChildren(List<GroupState> children) {
        this.children = children;
    }
}
