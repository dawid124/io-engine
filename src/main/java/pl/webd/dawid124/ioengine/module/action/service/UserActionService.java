package pl.webd.dawid124.ioengine.module.action.service;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiActionRequest;
import pl.webd.dawid124.ioengine.module.action.model.server.ServerUiAction;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.EIoDriverType;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.service.StateService;
import pl.webd.dawid124.ioengine.mqtt.IoAction;
import pl.webd.dawid124.ioengine.mqtt.MqttService;

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

    public SceneState processSceneChange(String zoneId, String sceneId) {
        stateService.updateScene(zoneId, sceneId);

        SceneState sceneState = stateService.fetchScene(zoneId, sceneId);


        List<ServerUiAction> serverActions = actionDataService.fromSceneState(zoneId, sceneId);

        sendMqttActions(serverActions);

        return sceneState;
    }

    public UiActionRequest processActionChange(UiActionRequest action) {


        List<UiAction> groups = new ArrayList<>();
        List<UiAction> blinds = new ArrayList<>();
        List<UiAction> lights = new ArrayList<>();

        for (UiAction a: action.getActions()) {
            if (a.isGroup()) groups.add(a);
            else if (a.isBlind()) blinds.add(a);
            else lights.add(a);
        }

        if (!groups.isEmpty()) {
            List<ServerUiAction> groupActions = actionDataService.fromUiActionRequest(
                    new UiActionRequest(action.getZoneId(), action.getSceneId(), groups)
            );
            processLightGroups(groupActions);
        }
        if (!lights.isEmpty()) {
            List<ServerUiAction> lightsActions = actionDataService.fromUiActionRequest(
                    new UiActionRequest(action.getZoneId(), action.getSceneId(), lights)
            );
            processLights(lightsActions);
        }


        processBlinds(blinds);

        return action;
    }

    private void processLightGroups(List<ServerUiAction> actions) {
        actions.forEach(stateService::updateGroupSate);
        sendMqttActions(actions);
    }

    private void processLights(List<ServerUiAction> actions) {
        stateService.updateDeviceSate(actions);

        sendMqttActions(actions);
    }

    private void sendMqttActions(List<ServerUiAction> actions) {
        List<IoAction> allActions = new ArrayList<>();

        for (ServerUiAction action: actions) {
            allActions.addAll(action.toIoActions());
        }

        List<IoAction> mqttActions = allActions.stream()
                .filter(a -> EIoDriverType.MQTT.equals(a.getDeviceType()))
                .collect(Collectors.toList());

        mqttService.sendActionsToDevices(mqttActions);
    }

    private void processBlinds(List<UiAction> actions) {
        blindService.processBlinds(actions);
    }
}
