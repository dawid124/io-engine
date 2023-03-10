package pl.webd.dawid124.ioengine.module.action.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiActionRequest;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.EIoDriverType;
import pl.webd.dawid124.ioengine.module.state.model.device.NeoDeviceState;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.model.zone.ZoneState;
import pl.webd.dawid124.ioengine.module.state.service.StateService;
import pl.webd.dawid124.ioengine.mqtt.MqttService;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActionService {

    private static final Logger LOG = LogManager.getLogger( ActionService.class );

    private final StateService stateService;

    private final BlindService blindService;
    private final MqttService mqttService;
    private final ActionDataService actionDataService;

    public ActionService(StateService stateService, BlindService blindService, MqttService mqttService,
                         ActionDataService actionDataService) {
        this.mqttService = mqttService;
        this.stateService = stateService;
        this.blindService = blindService;
        this.actionDataService = actionDataService;
    }

    public void processSimpleActions(List<UiAction> actions) {
        List<IoAction> mqttActions = actionDataService.fromUiAction(actions);

        mqttService.sendActionsToDevices(mqttActions);
    }

    public SceneState processSceneChange(String zoneId, String sceneId) {
        stateService.updateScene(zoneId, sceneId);

        SceneState sceneState = stateService.fetchScene(zoneId, sceneId);


        List<IoAction> actions= actionDataService.fromSceneState(sceneState);

        sendMqttActions(actions);

        return sceneState;
    }

    public SceneState chngeLightPercent(String zoneId, int percent) {
        int val = (int) ((percent / (float) 100) * 255);
        ZoneState zone = stateService.getZoneState().get(zoneId);
        SceneState scece = zone.getSceneStates().get(zone.getActiveScene());
        scece.getGroupState().forEach((name, gs) -> {
            UiAction action = new UiAction();
            if (gs.getState() instanceof NeoDeviceState) {
                NeoDeviceState state = (NeoDeviceState) gs.getState();
                action.setAnimationId(state.getAnimationId());
                action.setSpeed(state.getSpeed());
            }
            action.setBrightness(val);
            stateService.updateSateByUiAction(gs, action);
        });
        SceneState sceneState = stateService.fetchScene(zoneId, zone.getActiveScene());


        List<IoAction> actions= actionDataService.fromSceneState(sceneState);

        sendMqttActions(actions);

        return sceneState;
    }

    public void sendDriverState(String driverId) {
        List<IoAction> serverActions = actionDataService.fromDriverState(driverId);
        sendMqttActions(serverActions);
    }

    public UiActionRequest processActionChange(UiActionRequest action) {

        List<UiAction> blinds = new ArrayList<>();
        List<UiAction> lights = new ArrayList<>();

        if (action.getActions() != null) {
            for (UiAction a: action.getActions()) {
                if (a.isBlind()) blinds.add(a);
                else lights.add(a);
            }
        }

        processBlinds(blinds);

        action.setActions(lights);
        processLights(action);

        return action;
    }


    public void processLights(UiActionRequest action) {
        stateService.updateSateByUiAction(action);

        if (stateService.getZoneState().get(action.getZoneId()).getActiveScene().equals(action.getSceneId())) {
            sendMqttActions(actionDataService.fromUiActionRequest(action));
        }
    }

    private void sendMqttActions(List<IoAction> allActions) {

        List<IoAction> mqttActions = allActions.stream()
                .filter(a -> EIoDriverType.MQTT.equals(a.getDeviceType()))
                .collect(Collectors.toList());

        mqttService.sendActionsToDevices(mqttActions);
    }

    public void processBlinds(List<UiAction> actions) {

        List<IoAction> mqttActions = blindService.processBlinds(actions);

        mqttService.sendActionsToDevices(mqttActions);
    }
}
