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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class TriggerService implements MessageHandler {

    private final Gson gson = new Gson();

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

    private final MqttService mqttService;
    private final StateService stateService;
    private final DeviceService deviceService;

    ScheduledFuture<?> officeFuture;
    ScheduledFuture<?> kitchenFuture;

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
        boolean isOfficeAuto = "auto".equals(office.getActiveScene());

        ZoneStateResponse floor1 = stateService.fetchZoneStates().getZones().get("floor1");
        boolean isFloor1Auto = "auto".equals(floor1.getActiveScene());

        int officeBrightness = 255;
        int offTime = 60 * 2;
        LocalDateTime now = LocalDateTime.now();
        boolean night = false;
        if (now.getHour() >= 22 || now.getHour() <= 7) {
            officeBrightness = 25;
            offTime = 1;
            night = true;
        }

        switch (trigger.getId()) {
            case "pir-office":
                if (!isOfficeAuto) {
                    return;
                }

                if (officeFuture != null) {
                    officeFuture.cancel(false);
                }
                if (trigger.isState()) {
                    officeLight(ioAction, officeBrightness, 1000);
                    officeFuture = null;
                } else {
                    officeFuture = scheduler.schedule(() -> {
                        officeLight(ioAction, 0, 1000);
                    }, offTime, TimeUnit.SECONDS);
                }

                break;
            case "pir-lobby":
                String ioId = "rgbw-lobby";
                IDevice iDevice = deviceService.fetchDevice(ioId);
                ioAction.setDeviceId(iDevice.getDriverConfiguration().getDriver().getId());
                ioAction.setDeviceType(iDevice.getDriverConfiguration().getDriver().getType());

                ioAction.setIoId(ioId);
                ioAction.setIoType(EDeviceType.RGBW);
                ioAction.setAction(EActionType.CHANGE);
                ioAction.setColor(new Color(0, 0, 0, 255));
                ioAction.setTime(3000);
                if (trigger.isState()) {
                    ioAction.setTime(1000);
                    ioAction.setBrightness(255);
                } else {
                    ioAction.setTime(3000);
                    ioAction.setBrightness(0);
                }

                mqttService.sendActionsToDevices(Collections.singletonList(ioAction));
                break;
            case "pir-wc":

                break;

            case "pir-kitchen":
                if (!isFloor1Auto) {
                    return;
                }

                if (kitchenFuture != null) {
                    kitchenFuture.cancel(false);
                }
                if (trigger.isState()) {
                    kitchenLight(true, 1000);
                    kitchenFuture = null;
                } else {
                    kitchenFuture = scheduler.schedule(() -> {
                        kitchenLight( false, 1000);
                    }, 60, TimeUnit.SECONDS);
                }

                break;

        }
    }

    private void officeLight(IoAction ioAction, int officeBrightness, int time) {
        String ioId = "rgbw-office";
        IDevice iDevice = deviceService.fetchDevice(ioId);
        ioAction.setDeviceId(iDevice.getDriverConfiguration().getDriver().getId());
        ioAction.setDeviceType(iDevice.getDriverConfiguration().getDriver().getType());

        ioAction.setIoId(ioId);
        ioAction.setIoType(EDeviceType.RGBW);
        ioAction.setAction(EActionType.CHANGE);
        ioAction.setColor(new Color(0, 0, 0, 255));
        ioAction.setTime(time);
        ioAction.setBrightness(officeBrightness);

        mqttService.sendActionsToDevices(Collections.singletonList(ioAction));
    }

    private void kitchenLight(boolean on, int time) {
        ArrayList<IoAction> actions = new ArrayList<>();

        IoAction rgbwwAction = new IoAction();
        IDevice rgbwwDevice = deviceService.fetchDevice("rgbww-kitchen");
        rgbwwAction.setDeviceId(rgbwwDevice.getDriverConfiguration().getDriver().getId());
        rgbwwAction.setDeviceType(rgbwwDevice.getDriverConfiguration().getDriver().getType());

        rgbwwAction.setIoId("rgbww-kitchen");
        rgbwwAction.setIoType(EDeviceType.RGBW);
        rgbwwAction.setAction(EActionType.CHANGE);
        rgbwwAction.setColor(new Color(0, 0, 0, 255));
        rgbwwAction.setTime(time);
        rgbwwAction.setBrightness(on ? 100 : 0);
        actions.add(rgbwwAction);

        IoAction rgbwAction = new IoAction();
        IDevice rgbwDevice = deviceService.fetchDevice("rgbw-kitchen");
        rgbwAction.setDeviceId(rgbwDevice.getDriverConfiguration().getDriver().getId());
        rgbwAction.setDeviceType(rgbwDevice.getDriverConfiguration().getDriver().getType());

        rgbwAction.setIoId("rgbw-kitchen");
        rgbwAction.setIoType(EDeviceType.RGBW);
        rgbwAction.setAction(EActionType.CHANGE);
        rgbwAction.setColor(new Color(0, 0, 0, 255));
        rgbwAction.setTime(time);
        rgbwAction.setBrightness(on ? 15 : 0);
        actions.add(rgbwAction);


        IoAction neoAction = new IoAction();
        IDevice neoDevice = deviceService.fetchDevice("neo-kitchen");
        neoAction.setDeviceId(neoDevice.getDriverConfiguration().getDriver().getId());
        neoAction.setDeviceType(neoDevice.getDriverConfiguration().getDriver().getType());

        neoAction.setIoId("neo-kitchen");
        neoAction.setIoType(EDeviceType.NEO);
        neoAction.setAction(EActionType.CHANGE);
        neoAction.setColor(new Color(0, 0, 0, 255));
        neoAction.setAnimationId(0);
        neoAction.setTime(time);
        neoAction.setBrightness(on ? 10 : 0);
        actions.add(neoAction);

        mqttService.sendActionsToDevices(actions);
    }
}
