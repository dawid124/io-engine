package pl.webd.dawid124.ioengine.module.state.model.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GroupState implements Serializable {

    private boolean hidden;
    private DeviceState state;
    private ArrayList<GroupState> children;

    public GroupState(DeviceState state, ArrayList<GroupState> children) {
        this.state = state;
        this.children = children;
    }

    public GroupState(DeviceState state, ArrayList<GroupState> children, boolean hidden) {
        this.hidden = hidden;
        this.state = state;
        this.children = children;
    }

    public DeviceState getState() {
        return state;
    }

    public ArrayList<GroupState> getChildren() {
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

    public void setChildren(ArrayList<GroupState> children) {
        this.children = children;
    }
}
