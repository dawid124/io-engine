package pl.webd.dawid124.ioengine.module.driversync;

import com.google.gson.Gson;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.service.ActionService;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.mqtt.MqttService;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfig;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverSyncService implements MessageHandler {

    private final Gson gson = new Gson();

    private final MqttService mqttService;
    private final DeviceService deviceService;
    private final ActionService userActionService;

    public DriverSyncService(MqttService mqttService, DeviceService deviceService, ActionService userActionService) {
        this.mqttService = mqttService;
        this.deviceService = deviceService;
        this.userActionService = userActionService;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        DriverSyncMsg trigger = gson.fromJson((String) message.getPayload(), DriverSyncMsg.class);

        switch (trigger.getType()) {
            case READY_FOR_CONFIG:
                processReadyForConfig(trigger);
                break;
            case READY_FOR_INITIAL_STATE:
                processWaitingForInitialState(trigger);
                break;
            case WORKING:

                break;
        }
    }

    private void processReadyForConfig(DriverSyncMsg trigger) {
        List<IoConfig> configs = deviceService.fetchDevicesByDriverId(trigger.getId()).values().stream()
                .map(IDevice::toIoConfig)
                .collect(Collectors.toList());

        mqttService.sendConfigActionToDevice(trigger.getId(), configs);
    }

    private void processWaitingForInitialState(DriverSyncMsg trigger) {
        userActionService.sendDriverState(trigger.getId());
    }

}
