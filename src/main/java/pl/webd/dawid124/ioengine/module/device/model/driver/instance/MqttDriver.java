package pl.webd.dawid124.ioengine.module.device.model.driver.instance;

public class MqttDriver implements IDriver {

    private final String id;
    private final String topic;

    public MqttDriver(String id, String topic) {
        this.id = id;
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    @Override public String getId() {
        return id;
    }

    @Override public EIoDriverType getType() {
        return EIoDriverType.ZIGBEE_MQTT;
    }
}
