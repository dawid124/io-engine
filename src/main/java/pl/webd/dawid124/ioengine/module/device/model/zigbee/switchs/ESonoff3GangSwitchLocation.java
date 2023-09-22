package pl.webd.dawid124.ioengine.module.device.model.zigbee.switchs;

public enum ESonoff3GangSwitchLocation {
    L1("state_l1"), L2("state_l2"), L3("state_l3");

    private final String stateKey;

    ESonoff3GangSwitchLocation(String stateKey) {
        this.stateKey = stateKey;
    }

    public String getStateKey() {
        return stateKey;
    }
}
