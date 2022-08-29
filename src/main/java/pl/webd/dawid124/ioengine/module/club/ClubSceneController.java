package pl.webd.dawid124.ioengine.module.club;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.rest.Color;
import pl.webd.dawid124.ioengine.module.action.model.rest.EActionType;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.EIoDriverType;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.module.state.service.StateService;
import pl.webd.dawid124.ioengine.mqtt.MqttService;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ClubSceneController {
    private static final Logger LOG = LogManager.getLogger( ClubSceneController.class );
    boolean testState;
    private MqttService mqttService;
    private DeviceService deviceService;

    private StateService stateService;
    private boolean active;

    int aIndex = 0;
    int[] annimations = {8,11,12,17,30,41,47,51,52};
    int[] brightness = {0,10,50,90,150,200,255};
    int annimationId = 0;

    public ClubSceneController(MqttService mqttService, DeviceService deviceService, StateService stateService) {
        this.mqttService = mqttService;
        this.deviceService = deviceService;
        this.stateService = stateService;
    }

//    @Scheduled(fixedDelay = 5000)
    public void checkSceneChange() {
        boolean clubActive = "club".equals(stateService.getZoneState().get("floor1").getActiveScene());
        if (clubActive && !active) {
            this.active = true;
        } else if (!clubActive && active) {
            this.active = false;
        }
    }

//    @Scheduled(fixedDelay = 1500)
    public void performanceTestWc() {
        mqttService.sendActionsToDevices(wcTest());
    }

//    @Scheduled(fixedDelay = 60)
    public void performanceTestNeoBrightness() {
        List<IoAction> actions = neoTest();

        int b = random(0, brightness.length - 1);

        actions.forEach(a -> {
            a.setBrightness(brightness[b]);
            a.setAnimationId(annimationId);
        });

        mqttService.sendActionsToDevices(actions);
    }

//    @Scheduled(fixedDelay = 5000)
    public void performanceTestNeoAnimation() {
        List<IoAction> actions = neoTest();
//        int index = random(0, annimations.length - 1);

        aIndex = aIndex + 1;
        if (aIndex >= annimations.length) aIndex = 0;

        annimationId = annimations[aIndex];
        LOG.error("animationId: " + annimationId);
        actions.forEach(a -> {
            a.setAnimationId(annimationId);

        });
        mqttService.sendActionsToDevices(actions);
    }

//    13,16,14, 1, 43, 18,23,55,3,19,25,50,56


    private List<IoAction> neoTest() {
        List<IoAction> list = new ArrayList<>();

        IDevice n1 = deviceService.fetchDevice("neo-wall-1");
        IDevice n2 = deviceService.fetchDevice("neo-wall-2");
        IDevice n3 = deviceService.fetchDevice("neo-wall-3");
        IDevice n4 = deviceService.fetchDevice("neo-wall-4");

        IoAction n1Action = new IoAction(
                n1.getId(),
                n1.getIoType(),
                EActionType.CHANGE,
                new Color(0,0,0,255),
                0,
                0,0,
                0, n1.getDriverConfiguration().getDriver().getId(),
                EIoDriverType.MQTT
        );
        IoAction n2Action = new IoAction(
                n2.getId(),
                n2.getIoType(),
                EActionType.CHANGE,
                new Color(0,0,0,255),
                0,
                0,0,
                0, n2.getDriverConfiguration().getDriver().getId(),
                EIoDriverType.MQTT
        );
        IoAction n3Action = new IoAction(
                n3.getId(),
                n3.getIoType(),
                EActionType.CHANGE,
                new Color(0,0,0,255),
                0,
                0,0,
                0, n3.getDriverConfiguration().getDriver().getId(),
                EIoDriverType.MQTT
        );
        IoAction n4Action = new IoAction(
                n4.getId(),
                n4.getIoType(),
                EActionType.CHANGE,
                new Color(0,0,0,255),
                0,
                0,0,
                0, n4.getDriverConfiguration().getDriver().getId(),
                EIoDriverType.MQTT
        );


        list.add(n1Action);
        list.add(n2Action);
        list.add(n3Action);
        list.add(n4Action);

        return list;
    }

    private List<IoAction> wcTest() {
        testState = !testState;

        IDevice wc1 = deviceService.fetchDevice("rgbw-wc1");
        IDevice wc2 = deviceService.fetchDevice("rgbw-wc2");

        IoAction wc1Action = new IoAction(
                wc1.getId(),
                wc1.getIoType(),
                EActionType.CHANGE,
                new Color(0,0,0,255),
                0,
                0,0,
                0, wc1.getDriverConfiguration().getDriver().getId(),
                EIoDriverType.MQTT
        );
        IoAction wc2Action = new IoAction(
                wc2.getId(),
                wc2.getIoType(),
                EActionType.CHANGE,
                new Color(0,0,0,255),
                0,
                0,0,
                0, wc2.getDriverConfiguration().getDriver().getId(),
                EIoDriverType.MQTT
        );
        List<IoAction> list = new ArrayList<>();
        list.add(wc1Action);
        list.add(wc2Action);

//        wc1Action.setTime(500);
//        wc2Action.setTime(500);

        if (testState) {
            wc1Action.setBrightness(255);
            wc2Action.setBrightness(255);
        }
        return list;
    }

    public int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
