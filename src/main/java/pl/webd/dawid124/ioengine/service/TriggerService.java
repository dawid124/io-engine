package pl.webd.dawid124.ioengine.service;

import com.google.gson.Gson;
import org.springframework.integration.support.MutableMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.config.MqttConfig;
import pl.webd.dawid124.ioengine.home.devices.output.EDeviceType;
import pl.webd.dawid124.ioengine.home.state.Color;
import pl.webd.dawid124.ioengine.model.IoAction;
import pl.webd.dawid124.ioengine.model.IoActionRequest;
import pl.webd.dawid124.ioengine.model.SensorTrigger;
import pl.webd.dawid124.ioengine.mqtt.MqttService;

import java.util.Collections;

@Service
public class TriggerService implements MessageHandler {

    private final Gson gson = new Gson();

    private final MqttConfig.MqttGateway mqttGateway;
    private final MqttService mqttService;

    public TriggerService(MqttConfig.MqttGateway mqttGateway, MqttService mqttService) {
        this.mqttGateway = mqttGateway;
        this.mqttService = mqttService;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
//        System.out.println(message.getPayload());

        SensorTrigger trigger = gson.fromJson((String) message.getPayload(), SensorTrigger.class);
        IoAction ioAction = new IoAction();

        switch (trigger.getId()) {
            case "pir-office":
                ioAction.setIoId("rgbw-office");
                ioAction.setIoType(EDeviceType.RGBW);
                ioAction.setColor(new Color(0, 0, 0, 255));
                ioAction.setTime(1500);
                if (trigger.isState()) {
                    ioAction.setBrightness(255);
                } else {
                    ioAction.setBrightness(0);
                }
                mqttGateway.sendToMqtt(new MutableMessage(gson.toJson(new IoActionRequest(Collections.singletonList(ioAction)))));

                break;
            case "pir-lobby":
                ioAction.setIoId("rgbw-lobby");
                ioAction.setIoType(EDeviceType.RGBW);
                ioAction.setColor(new Color(0, 0, 0, 255));
                ioAction.setTime(1500);
                if (trigger.isState()) {
                    ioAction.setBrightness(255);
                } else {
                    ioAction.setBrightness(0);
                }
                mqttGateway.sendToMqtt(new MutableMessage(gson.toJson(new IoActionRequest(Collections.singletonList(ioAction)))));
                break;
            case "pir-wc":

                break;

            case "pir-kitchen":

                break;

        }
    }
}
