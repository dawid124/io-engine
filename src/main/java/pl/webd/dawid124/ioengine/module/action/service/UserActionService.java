package pl.webd.dawid124.ioengine.module.action.service;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiActionRequest;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.EIoDriverType;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.service.StateService;
import pl.webd.dawid124.ioengine.mqtt.MqttService;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserActionService {

    private final StateService stateService;

    private final BlindService blindService;
    private final MqttService mqttService;
    private final ActionDataService actionDataService;

    public UserActionService(StateService stateService, BlindService blindService, MqttService mqttService,
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


    private void processLights(UiActionRequest action) {
        stateService.updateSateByUiAction(action);

        List<IoAction> ioActions = actionDataService.fromUiActionRequest(action);
        sendMqttActions(ioActions);
    }

    private void sendMqttActions(List<IoAction> allActions) {

        List<IoAction> mqttActions = allActions.stream()
                .filter(a -> EIoDriverType.MQTT.equals(a.getDeviceType()))
                .collect(Collectors.toList());

        mqttService.sendActionsToDevices(mqttActions);
    }

    private void processBlinds(List<UiAction> actions) {

        List<IoAction> mqttActions = blindService.processBlinds(actions);

        mqttService.sendActionsToDevices(mqttActions);
    }
}
