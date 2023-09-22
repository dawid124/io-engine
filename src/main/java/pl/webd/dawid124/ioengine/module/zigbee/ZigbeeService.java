package pl.webd.dawid124.ioengine.module.zigbee;


import com.google.gson.Gson;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.automation.trigger.SensorTriggerMsg;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.device.model.zigbee.ZigbeeDevice;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;

@Service
public class ZigbeeService implements MessageHandler {

    public static final String MQTT_RECEIVED_TOPIC = "mqtt_receivedTopic";
    private Gson gson = new Gson();

    private final AutomationContext context;
    private final DeviceService deviceService;

    public ZigbeeService(AutomationContext context, DeviceService deviceService) {
        this.context = context;
        this.deviceService = deviceService;
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

        ZigbeeDevice zigbeeDevice = (ZigbeeDevice) iDevice;
        zigbeeDevice.processIncomingMsg(context, message);
    }
}
