package pl.webd.dawid124.ioengine.mqtt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MutableMessage;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.config.MqttConfig;
import pl.webd.dawid124.ioengine.config.settings.MqttSettings;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.EIoDriverType;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.IDriver;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.MqttDriver;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.device.model.zigbee.ZigbeeAction;
import pl.webd.dawid124.ioengine.module.device.model.zigbee.ZigbeeDevice;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;
import pl.webd.dawid124.ioengine.mqtt.action.IoActionRequest;
import pl.webd.dawid124.ioengine.mqtt.action.adapter.IoActionJsonAdapter;
import pl.webd.dawid124.ioengine.mqtt.action.adapter.IoActionJsonPartyAdapter;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfig;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfigRequest;

import java.util.*;
import java.util.stream.Collectors;

@Service
public final class MqttService {

    private static final String SEPARATOR = "/";
    private final Gson gson;
    private final Gson gsonParty;

    private final MqttSettings settings;
    private final MqttConfig.MqttGateway mqttGateway;

    private final DeviceService deviceService;

    public MqttService(MqttSettings settings, MqttConfig.MqttGateway mqttGateway, DeviceService deviceService) {
        this.settings = settings;
        this.mqttGateway = mqttGateway;
        this.deviceService = deviceService;

        this.gson = new GsonBuilder()
                .registerTypeAdapter(IoAction.class, new IoActionJsonAdapter())
                .create();

        this.gsonParty = new GsonBuilder()
                .registerTypeAdapter(IoAction.class, new IoActionJsonPartyAdapter())
                .create();
    }

    public void sendActionsToDevicesParty(List<IoAction> actions) {
        Map<String, List<IoAction>> actionsPerAddress = getActionsPerDeviceId(actions);
        actionsPerAddress.forEach((id, values) ->
                mqttGateway.sendToMqtt(mutableParty(new IoActionRequest(values), prepareDeviceActionTopic(id))));
    }

    public void sendActionsToDevices(List<IoAction> actions) {
        sendActionsToPicoDevices(actions.stream()
                .filter(a -> EIoDriverType.MQTT.equals(a.getDeviceType())).collect(Collectors.toList()));

        sendActionsToZigbeeDevices(actions.stream()
                .filter(a -> EIoDriverType.ZIGBEE_MQTT.equals(a.getDeviceType())).collect(Collectors.toList()));
    }

    private static Map<String, List<IoAction>> getActionsPerDeviceId(List<IoAction> actions) {
        Map<String, List<IoAction>> actionsPerAddress = new HashMap<>();

        for (IoAction action: actions) {
            List<IoAction> group = actionsPerAddress.computeIfAbsent(action.getDeviceId(), k -> new ArrayList<>());
            group.add(action);
        }
        return actionsPerAddress;
    }

    private void sendActionsToZigbeeDevices(List<IoAction> actions) {
        for (IoAction action : actions) {
            IDevice iDevice = deviceService.fetchDevice(action.getIoId());
            IDriver iDriver = iDevice.getDriverConfiguration().getDriver();
            if (iDevice instanceof ZigbeeDevice && iDriver instanceof MqttDriver) {
                ZigbeeDevice zigbeeDevice = (ZigbeeDevice) iDevice;
                ZigbeeAction zigbeeAction = zigbeeDevice.processAction(action.getZigbeeAction(), null);

                MutableMessage<String> message = new MutableMessage<>(
                        zigbeeAction.getActionJson(),
                        getTopicHeader(prepareZigbeeDeviceActionTopic(((MqttDriver) iDriver).getTopic(), zigbeeAction.getQueueSuffix())));

                mqttGateway.sendToMqtt(message);
            }
        }
    }

    private void sendActionsToPicoDevices(List<IoAction> actions) {
        Map<String, List<IoAction>> actionsPerAddress = getActionsPerDeviceId(actions);

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

    private MutableMessage<String> mutableParty(Object body, String topic) {
        String content = gsonParty.toJson(body);

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

    private String prepareZigbeeDeviceActionTopic(String topicPrefix, String suffix) {
        return topicPrefix + SEPARATOR + suffix;
    }
}
