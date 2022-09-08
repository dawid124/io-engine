package pl.webd.dawid124.ioengine.module.club;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.rest.Color;
import pl.webd.dawid124.ioengine.module.action.model.rest.EActionType;
import pl.webd.dawid124.ioengine.module.club.fft.Recorder;
import pl.webd.dawid124.ioengine.module.club.fft.Visualizer;
import pl.webd.dawid124.ioengine.module.club.model.EEffectType;
import pl.webd.dawid124.ioengine.module.club.model.EToneType;
import pl.webd.dawid124.ioengine.module.club.model.SoundLightDefinition;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.EIoDriverType;
import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.module.state.service.StateService;
import pl.webd.dawid124.ioengine.mqtt.MqttService;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

import static pl.webd.dawid124.ioengine.module.club.fft.Visualizer.BAR_NUM;

@Service
public class SoundEngine {

    private static final Logger LOG = LogManager.getLogger( SoundEngine.class );


    private final MqttService mqttService;
    private final DeviceService deviceService;
    private final StateService stateService;
    private final SoundDefinitionService soundDefinitionService;


    private Recorder recorder;

    private static final String SOUND = "sound";

    private boolean active;

    private Thread recordThread;

    private final EDeviceType[] ledTypes = {EDeviceType.RGBW, EDeviceType.LED, EDeviceType.CCT, EDeviceType.NEO};
    private final String[] ids = {"w1", "w2", "w3", "w4", "w5", "w6", "w7"};



    int[] prevValuesB1 = new int[BAR_NUM];
    int[] prevValuesB2 = new int[BAR_NUM];
    int[] prevValuesB3 = new int[BAR_NUM];
    int[] prevValuesB4 = new int[BAR_NUM];
    int[] prevValuesB5 = new int[BAR_NUM];
    int[] prevValuesB10 = new int[BAR_NUM];
    int[] prevValuesB15 = new int[BAR_NUM];

    public SoundEngine(MqttService mqttService, DeviceService deviceService, StateService stateService,
                       SoundDefinitionService soundDefinitionService) {

        this.mqttService = mqttService;
        this.deviceService = deviceService;
        this.stateService = stateService;
        this.soundDefinitionService = soundDefinitionService;

        Visualizer.get();
    }



    @Scheduled(fixedDelay = 1000)
    public void checkScene() {
        boolean clubActive = SOUND.equals(stateService.getZoneState().get("floor1").getActiveScene());
        if (clubActive && !active) {
            this.active = true;
            startRecording();
        } else if (!clubActive && active) {
            this.active = false;
            recorder.stop();
            recordThread.interrupt();
            recordThread = null;
        }
    }


    private void startRecording() {
        recorder = new Recorder();
        recordThread = new Thread(() -> {
            try {
                recorder.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        recordThread.start();
    }

    @Scheduled(fixedDelay = 60)
    public void sendMsg() {
        if (!active || Recorder.values == null || Recorder.values.length == 0) return;

        int[] valuesA = new int[BAR_NUM];
        int[] valuesB1 = new int[BAR_NUM];
        int[] valuesB2 = new int[BAR_NUM];
        int[] valuesB3 = new int[BAR_NUM];
        int[] valuesB4 = new int[BAR_NUM];
        int[] valuesB5 = new int[BAR_NUM];
        int[] valuesB10 = new int[BAR_NUM];
        int[] valuesB15 = new int[BAR_NUM];


        for (int i = 0; i < BAR_NUM; i++) {
            int newValue = recorder.values[i].readMax();
            valuesA[i] = newValue;
            int prevValue = prevValuesB1[i];
            if (newValue < prevValue) {
                int diff = newValue - prevValue;
                valuesB1[i] = prevValue + Math.max(-1, diff);
            } else  {
                valuesB1[i] = newValue;
            }
            if (newValue < prevValue) {
                int diff = newValue - prevValue;
                valuesB2[i] = prevValue + Math.max(-2, diff);
            } else  {
                valuesB2[i] = newValue;
            }
            if (newValue < prevValue) {
                int diff = newValue - prevValue;
                valuesB3[i] = prevValue + Math.max(-3, diff);
            } else  {
                valuesB3[i] = newValue;
            }
            if (newValue < prevValue) {
                int diff = newValue - prevValue;
                valuesB4[i] = prevValue + Math.max(-4, diff);
            } else  {
                valuesB4[i] = newValue;
            }
            if (newValue < prevValue) {
                int diff = newValue - prevValue;
                valuesB5[i] = prevValue + Math.max(-5, diff);
            } else  {
                valuesB5[i] = newValue;
            }
            if (newValue < prevValue) {
                int diff = newValue - prevValue;
                valuesB10[i] = prevValue + Math.max(-10, diff);
            } else  {
                valuesB10[i] = newValue;
            }
            if (newValue < prevValue) {
                int diff = newValue - prevValue;
                valuesB15[i] = prevValue + Math.max(-15, diff);
            } else  {
                valuesB15[i] = newValue;
            }


        }
        prevValuesB1 = valuesB1;
        prevValuesB2 = valuesB2;
        prevValuesB3 = valuesB3;
        prevValuesB4 = valuesB4;
        prevValuesB5 = valuesB5;
        prevValuesB10 = valuesB10;
        prevValuesB15 = valuesB15;

        List<IoAction> list = new ArrayList<>();

        for (SoundLightDefinition definition: soundDefinitionService.getCurrentPreset().getLightDefinitions()) {
            if (!definition.isActive()) continue;

            IDevice device = deviceService.fetchDevice(definition.getIoId());

            IoAction action = new IoAction();
            action.setIoId(definition.getIoId());
            action.setIoType(device.getIoType());
            action.setDeviceType(EIoDriverType.MQTT);
            action.setDeviceId(device.getDriverConfiguration().getDriver().getId());
            action.setStepTime(0);
            action.setTime(0);
            action.setDelay(0);

            action.setColor(definition.getColor());


            int value = 0;
            if (EToneType.TYPE_A.equals(definition.getToneType())) {
                value = valuesA[definition.getToneIndex()];
            } else if (EToneType.TYPE_B1.equals(definition.getToneType())) {
                value = valuesB1[definition.getToneIndex()];
            } else if (EToneType.TYPE_B2.equals(definition.getToneType())) {
                value = valuesB2[definition.getToneIndex()];
            } else if (EToneType.TYPE_B3.equals(definition.getToneType())) {
                value = valuesB3[definition.getToneIndex()];
            } else if (EToneType.TYPE_B4.equals(definition.getToneType())) {
                value = valuesB4[definition.getToneIndex()];
            } else if (EToneType.TYPE_B5.equals(definition.getToneType())) {
                value = valuesB5[definition.getToneIndex()];
            } else if (EToneType.TYPE_B10.equals(definition.getToneType())) {
                value = valuesB10[definition.getToneIndex()];
            } else if (EToneType.TYPE_B15.equals(definition.getToneType())) {
                value = valuesB15[definition.getToneIndex()];
            }
            value = (int) ((double) value * definition.getScale());
            value = value < definition.getThreshold() ? definition.getMinBrightness() : value;

            if (EEffectType.ANIMATION_VALUE.equals(definition.getEffect())) {
                action.setAnimationId(definition.getAnimationId());
                action.setStaticSubModeId(definition.getSubAnimationId());
                action.setIntParam(definition.getStaticLeds() + value);
                action.setBrightness(definition.getInitialBrightness());
            } else if (EEffectType.BRIGHTNESS.equals(definition.getEffect())) {
                action.setAnimationId(-1);
                action.setStaticSubModeId(-1);
                action.setBrightness(Math.min(value, 255));
            }


            list.add(action);
        }

        mqttService.sendActionsToDevicesParty(list);
    }

    @PreDestroy
    public void destructor() {
        if (recorder != null) recorder.stop();
    }
}
