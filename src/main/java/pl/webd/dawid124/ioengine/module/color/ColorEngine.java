package pl.webd.dawid124.ioengine.module.color;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.rest.Color;
import pl.webd.dawid124.ioengine.module.club.SoundDefinitionService;
import pl.webd.dawid124.ioengine.module.club.fft.Recorder;
import pl.webd.dawid124.ioengine.module.club.fft.Visualizer;
import pl.webd.dawid124.ioengine.module.club.model.SoundLightDefinition;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.EIoDriverType;
import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.module.state.model.device.*;
import pl.webd.dawid124.ioengine.module.state.model.zone.ZoneState;
import pl.webd.dawid124.ioengine.module.state.service.StateService;
import pl.webd.dawid124.ioengine.mqtt.MqttService;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ColorEngine {

    private static final Logger LOG = LogManager.getLogger( ColorEngine.class );
    public static final String SOUND_VISUALIZER_ACTIVE = "sound-visualizer-active";


    private final MqttService mqttService;
    private final DeviceService deviceService;
    private final StateService stateService;
    private final SoundDefinitionService soundDefinitionService;


    private Recorder recorder;

    private static final String COLOR = "color";

    private boolean active;

    private Thread recordThread;

    private final EDeviceType[] ledTypes = {EDeviceType.RGBW, EDeviceType.LED, EDeviceType.CCT, EDeviceType.NEO};

    public ColorEngine(MqttService mqttService, DeviceService deviceService, StateService stateService,
                       SoundDefinitionService soundDefinitionService) {

        this.mqttService = mqttService;
        this.deviceService = deviceService;
        this.stateService = stateService;
        this.soundDefinitionService = soundDefinitionService;

        Visualizer.get();
    }

    @PostConstruct
    public void init() {

    }


    @Scheduled(fixedDelay = 1000)
    public void checkScene() {
        boolean clubActive = COLOR.equals(stateService.getZoneState().get("floor1").getActiveScene());
        if (clubActive && !active) {
            this.active = true;
        } else if (!clubActive && active) {
            this.active = false;
        }
    }

    @Scheduled(fixedDelay = 125)
    public void sendMsg() {
        if (!active) return;

        List<IoAction> list = new ArrayList<>();
        for (SoundLightDefinition definition: soundDefinitionService.getCurrentPreset().getLightDefinitions()) {
            if (!definition.isActive()) continue;

            IDevice device = deviceService.fetchDevice(definition.getIoId());
            ZoneState floor1 = stateService.getZoneState().get("floor1");
            Optional<GroupState> stateOpt = floor1.getSceneStates().get(floor1.getActiveScene()).findStateById(definition.getIoId());

            if (!stateOpt.isPresent()) continue;

            DeviceState state = stateOpt.get().getState();

            if (!(state instanceof LedDeviceState)) {
                continue;
            }

            Color currentColor = null;
            if (state instanceof ColorLedDeviceState) {
                currentColor = ((ColorLedDeviceState) state).getColor();
            } else if (state instanceof NeoDeviceState) {
                currentColor = ((NeoDeviceState) state).getColor();
            }

            IoAction action = new IoAction();
            action.setIoId(definition.getIoId());
            action.setIoType(device.getIoType());
            action.setDeviceType(EIoDriverType.MQTT);
            action.setDeviceId(device.getDriverConfiguration().getDriver().getId());
            action.setStepTime(0);
            action.setTime(0);
            action.setDelay(0);
            action.setBrightness(((LedDeviceState) state).getBrightness());

            Color nextColor = getNextColor(currentColor, definition.getScale());
            action.setColor(nextColor);
            list.add(action);

            currentColor.setR(nextColor.getR());
            currentColor.setG(nextColor.getG());
            currentColor.setB(nextColor.getB());
        }

        mqttService.sendActionsToDevicesParty(list);
    }

    public Color getNextColor(Color c, double scale) {
        if (c == null) return new Color(0, 0,0,0);
        float[] hsb = java.awt.Color.RGBtoHSB(c.getR(), c.getG(), c.getB(), null);

        float next = (float) scale/360;
        float nextAngle = hsb[0] + next;

        int[] nextColor = HSBtoRGB(nextAngle, hsb[1], hsb[2]);
        return new Color(nextColor[0], nextColor[1], nextColor[2], 0);
    }

    public static int[] HSBtoRGB(float hue, float saturation, float brightness) {
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        } else {
            float h = (hue - (float)Math.floor(hue)) * 6.0f;
            float f = h - (float)java.lang.Math.floor(h);
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h) {
                case 0:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (t * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 1:
                    r = (int) (q * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 2:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (t * 255.0f + 0.5f);
                    break;
                case 3:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (q * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 4:
                    r = (int) (t * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 5:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (q * 255.0f + 0.5f);
                    break;
            }
        }
        return new int[]{r, g, b};
    }

    @PreDestroy
    public void destructor() {
        if (recorder != null) recorder.stop();
    }
}
