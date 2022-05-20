package pl.webd.dawid124.ioengine.mqtt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MutableMessage;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.config.MqttConfig;
import pl.webd.dawid124.ioengine.config.settings.MqttSettings;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;
import pl.webd.dawid124.ioengine.mqtt.action.IoActionRequest;
import pl.webd.dawid124.ioengine.mqtt.action.adapter.IoActionJsonAdapter;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfig;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfigRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public final class MqttService {

    private static final String SEPARATOR = "/";
    private final Gson gson;

    private final MqttSettings settings;
    private final MqttConfig.MqttGateway mqttGateway;

    public MqttService(MqttSettings settings, MqttConfig.MqttGateway mqttGateway) {
        this.settings = settings;
        this.mqttGateway = mqttGateway;

        this.gson = new GsonBuilder()
                .registerTypeAdapter(IoAction.class, new IoActionJsonAdapter())
                .create();
    }

    public void sendActionsToDevices(List<IoAction> actions) {

        Map<String, List<IoAction>> actionsPerAddress = new HashMap<>();

        for (IoAction action: actions) {
            List<IoAction> group = actionsPerAddress.computeIfAbsent(action.getDeviceId(), k -> new ArrayList<>());
            group.add(action);
        }

        actionsPerAddress.forEach((id, values) ->
                mqttGateway.sendToMqtt(mutable(new IoActionRequest(values), prepareDeviceActionTopic(id))));
    }

    public void sendConfigActionToDevice(String driverId, List<IoConfig> configs) {
        mqttGateway.sendToMqtt(mutable(new IoConfigRequest(configs), prepareDeviceActionTopic(driverId)));
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
