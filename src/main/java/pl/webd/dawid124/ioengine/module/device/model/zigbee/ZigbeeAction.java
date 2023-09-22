package pl.webd.dawid124.ioengine.module.device.model.zigbee;

public class ZigbeeAction {
    private final String queueSuffix;
    private final String actionJson;

    public ZigbeeAction(String queueSuffix, String actionJson) {
        this.queueSuffix = queueSuffix;
        this.actionJson = actionJson;
    }

    public String getQueueSuffix() {
        return queueSuffix;
    }

    public String getActionJson() {
        return actionJson;
    }
}
