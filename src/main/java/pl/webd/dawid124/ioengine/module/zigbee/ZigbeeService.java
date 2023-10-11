package pl.webd.dawid124.ioengine.module.zigbee;


import com.google.gson.*;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.device.model.zigbee.ZigbeeDevice;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.module.state.model.adapter.ZigbeeMessageAdapter;
import pl.webd.dawid124.ioengine.module.state.model.device.ZigbeeDeviceState;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.VariableFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ZigbeeService implements MessageHandler {

    public static final String MQTT_RECEIVED_TOPIC = "mqtt_receivedTopic";
    private final Gson gson;

    private final AutomationContext context;
    private final DeviceService deviceService;

    public ZigbeeService(AutomationContext context, DeviceService deviceService) {
        this.context = context;
        this.deviceService = deviceService;
        this.gson =  new GsonBuilder()
                .registerTypeAdapter(ZigbeeMessage.class, new ZigbeeMessageAdapter())
                .create();
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        MessageHeaders headers = message.getHeaders();

        String topic = (String) headers.get(MQTT_RECEIVED_TOPIC);
        String id = topic.substring(topic.lastIndexOf("/") + 1);
        IDevice iDevice = deviceService.fetchDevice(id);

        if (!(iDevice instanceof ZigbeeDevice)) {
            return;
        }

        ZigbeeDeviceState deviceState = (ZigbeeDeviceState)
                context.getStateService().getSensors().get(id);

        JsonElement msgJson = JsonParser.parseString((String) message.getPayload());

        updateState(msgJson, deviceState);

        runTrigger(msgJson, deviceState);

        ((ZigbeeDevice) iDevice).processIncomingMsg(context, message);
    }

    private void runTrigger(JsonElement msgJson, ZigbeeDeviceState deviceState) {
        Map<String, IVariable> variables = new HashMap<>();
        JsonObject obj = msgJson.getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();

        for (Map.Entry<String, JsonElement> entry: entries) {
            JsonElement value = entry.getValue();
            variables.put(entry.getKey(), VariableFactory.getVariable(value));
        }

        context.getTriggerService().handleZigbeeMessage(deviceState.getIoId(), variables);
    }

    private void updateState(JsonElement msgJson, ZigbeeDeviceState deviceState) {
        JsonObject obj = msgJson.getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
        for (Map.Entry<String, JsonElement> entry: entries) {
            try {
                Field field = findUnderlying(deviceState.getClass(), entry.getKey());
                field.setAccessible(true);
                if (entry.getValue().isJsonPrimitive()) {
                    switch (field.getType().getName()) {
                        case "string":
                            field.set(deviceState, entry.getValue().getAsString());
                            break;
                        case "boolean":
                            field.setBoolean(deviceState, entry.getValue().getAsBoolean());
                            break;
                        case "double":
                            field.set(deviceState, entry.getValue().getAsDouble());
                            break;
                        case "integer":
                            field.set(deviceState, entry.getValue().getAsInt());
                            break;
                        case "float":
                            field.set(deviceState, entry.getValue().getAsFloat());
                            break;
                        case "bigdecimal":
                            field.set(deviceState, entry.getValue().getAsBigDecimal());
                            break;
                    }
                }
            } catch (Exception e) {}
        }
        context.getStateService().updateDeviceState(deviceState.getIoId(), deviceState);
    }

    public static Field findUnderlying(Class<?> clazz, String fieldName) {
        Class<?> current = clazz;
        do {
            try {
                return current.getDeclaredField(fieldName);
            } catch(Exception e) {}
        } while((current = current.getSuperclass()) != null);
        return null;
    }
}
