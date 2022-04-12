package pl.webd.dawid124.ioengine.module.trigger;

import com.google.gson.Gson;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.rest.EActionType;
import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.module.share.Color;
import pl.webd.dawid124.ioengine.module.state.model.rest.ZoneStateResponse;
import pl.webd.dawid124.ioengine.module.state.service.StateService;
import pl.webd.dawid124.ioengine.mqtt.IoAction;
import pl.webd.dawid124.ioengine.mqtt.MqttService;

import java.util.Collections;

@Service
public class TriggerService implements MessageHandler {

    private final Gson gson = new Gson();

    private final MqttService mqttService;
    private final StateService stateService;
    private final DeviceService deviceService;

    public TriggerService(MqttService mqttService, StateService stateService, DeviceService deviceService) {
        this.mqttService = mqttService;
        this.stateService = stateService;
        this.deviceService = deviceService;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
//        System.out.println(message.getPayload());

        SensorTrigger trigger = gson.fromJson((String) message.getPayload(), SensorTrigger.class);
        IoAction ioAction = new IoAction();

        ZoneStateResponse office = stateService.fetchZoneStates().getZones().get("office");
        if (!"auto".equals(office.getActiveScene())) {
            return;
        }

        switch (trigger.getId()) {
            case "pir-office":
                String ioId = "rgbw-office";
                IDevice iDevice = deviceService.fetchDevice(ioId);
                ioAction.setDeviceId(iDevice.getDriverConfiguration().getDriver().getId());
                ioAction.setDeviceType(iDevice.getDriverConfiguration().getDriver().getType());

                ioAction.setIoId(ioId);
                ioAction.setIoType(EDeviceType.RGBW);
                ioAction.setAction(EActionType.CHANGE);
                ioAction.setColor(new Color(0, 0, 0, 255));
                ioAction.setTime(1000);
                if (trigger.isState()) {
                    ioAction.setBrightness(255);
                } else {
                    ioAction.setBrightness(0);
                }

                break;
            case "pir-lobby":
                ioId = "rgbw-lobby";
                iDevice = deviceService.fetchDevice(ioId);
                ioAction.setDeviceId(iDevice.getDriverConfiguration().getDriver().getId());
                ioAction.setDeviceType(iDevice.getDriverConfiguration().getDriver().getType());

                ioAction.setIoId(ioId);
                ioAction.setIoType(EDeviceType.RGBW);
                ioAction.setAction(EActionType.CHANGE);
                ioAction.setColor(new Color(0, 0, 0, 255));
                ioAction.setTime(1000);
                if (trigger.isState()) {
                    ioAction.setBrightness(255);
                } else {
                    ioAction.setBrightness(0);
                }
                break;
            case "pir-wc":

                break;

            case "pir-kitchen":

                break;

        }

        mqttService.sendActionsToDevices(Collections.singletonList(ioAction));
    }
}
