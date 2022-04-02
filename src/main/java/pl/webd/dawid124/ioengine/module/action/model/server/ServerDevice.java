package pl.webd.dawid124.ioengine.module.action.model.server;

import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;

import java.util.List;

public class ServerDevice {

    private final IDevice device;
    private final DeviceState state;
    private ServerDevice parent;
    private List<ServerDevice> children;

    private final boolean group;

    private ServerDevice(IDevice device, DeviceState state, ServerDevice parent,
                        List<ServerDevice> children, boolean group) {
        this.device = device;
        this.state = state;
        this.parent = parent;
        this.children = children;
        this.group = group;
    }

    public IDevice getDevice() {
        return device;
    }

    public DeviceState getState() {
        return state;
    }

    public ServerDevice getParent() {
        return parent;
    }

    public List<ServerDevice> getChildren() {
        return children;
    }

    public boolean isGroup() {
        return group;
    }

    public void setParent(ServerDevice parent) {
        this.parent = parent;
    }

    public void setChildren(List<ServerDevice> children) {
        this.children = children;
    }

    public static Builder group(DeviceState state) {
        return new Builder(state);
    }

    public static Builder standard(IDevice device, DeviceState state) {
        return new Builder(device, state);
    }

    public static class Builder {
        private IDevice device;
        private DeviceState state;
        private ServerDevice parent;
        private List<ServerDevice> children;

        private boolean group;

        Builder(IDevice device, DeviceState state) {
            this.device = device;
            this.state = state;
            this.group = false;
        }

        Builder(DeviceState state) {
            this.state = state;
            this.group = true;
        }

        public Builder setParent(ServerDevice parent) {
            this.parent = parent;

            return this;
        }

        public Builder addChildren(List<ServerDevice> children) {
            this.children = children;

            return this;
        }

        public ServerDevice build() {
            return new ServerDevice(device, state, parent, children, group);
        }
    }
}
