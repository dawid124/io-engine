package pl.webd.dawid124.ioengine.mqtt;

import com.google.gson.Gson;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MutableMessage;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.config.MqttConfig;
import pl.webd.dawid124.ioengine.config.settings.MqttSettings;
import pl.webd.dawid124.ioengine.module.action.model.ActionDevice;
import pl.webd.dawid124.ioengine.module.action.model.IoAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public final class MqttService {

    public static final String SEPARATOR = "/";
    private final Gson gson = new Gson();

    private final MqttSettings settings;
    private final MqttConfig.MqttGateway mqttGateway;

    public MqttService(MqttSettings settings, MqttConfig.MqttGateway mqttGateway) {
        this.settings = settings;
        this.mqttGateway = mqttGateway;
    }

    public void sendActionsToDevices(List<ActionDevice> actions) {

        Map<String, List<IoAction>> actionsPerAddress = new HashMap<>();

        for (ActionDevice actionDevice: actions) {
            String driverId = actionDevice.getDevice().getDriverConfiguration().getDriver().getId();
            List<IoAction> group = actionsPerAddress.computeIfAbsent(driverId, k -> new ArrayList<>());
            group.add(actionDevice.getIoAction());
        }

        actionsPerAddress.forEach((id, values) ->
                mqttGateway.sendToMqtt(mutable(new IoActionRequest(values), prepareDeviceActionTopic(id))));
    }

    private MutableMessage<String> mutable(Object body, String topic) {
        String content = gson.toJson(body);

        return new MutableMessage<>(content, getTopicHeader(topic));
    }

    private Map<String, Object> getTopicHeader(String topic) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(MqttHeaders.TOPIC, topic);
        return headers;
    }

    private String prepareDeviceActionTopic(String deviceId) {
        return settings.getActionTopic() + SEPARATOR + deviceId;
    }
}
