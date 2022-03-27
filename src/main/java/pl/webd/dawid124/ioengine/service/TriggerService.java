package pl.webd.dawid124.ioengine.service;

import com.google.gson.Gson;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.config.MqttConfig;
import pl.webd.dawid124.ioengine.home.devices.output.EDeviceType;
import pl.webd.dawid124.ioengine.home.state.Color;
import pl.webd.dawid124.ioengine.model.Action;
import pl.webd.dawid124.ioengine.model.ActionRequest;
import pl.webd.dawid124.ioengine.model.SensorTrigger;

import java.util.Collections;

@Service
public class TriggerService implements MessageHandler {

    private final Gson gson = new Gson();

    private final MqttConfig.MqttGateway mqttGateway;
    private final StateService stateService;

    public TriggerService(MqttConfig.MqttGateway mqttGateway, StateService stateService) {
        this.mqttGateway = mqttGateway;
        this.stateService = stateService;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        System.out.println(message.getPayload());

        SensorTrigger trigger = gson.fromJson((String) message.getPayload(), SensorTrigger.class);
        Action action = new Action();

        switch (trigger.getId()) {
            case "pir-office":
                action.setIoId("rgbw-office");
                action.setIoType(EDeviceType.RGBW);
                action.setColor(new Color(0, 0, 0, 255));
                action.setTime(1500);
                if (trigger.isState()) {
                    action.setBrightness(255);
                } else {
                    action.setBrightness(0);
                }
                mqttGateway.sendToMqtt(gson.toJson(new ActionRequest(Collections.singletonList(action))));

                break;
            case "pir-lobby":
                action.setIoId("rgbw-lobby");
                action.setIoType(EDeviceType.RGBW);
                action.setColor(new Color(0, 0, 0, 255));
                action.setTime(1500);
                if (trigger.isState()) {
                    action.setBrightness(255);
                } else {
                    action.setBrightness(0);
                }
                mqttGateway.sendToMqtt(gson.toJson(new ActionRequest(Collections.singletonList(action))));
                break;
            case "pir-wc":

                break;

            case "pir-kitchen":

                break;

        }
    }
}
