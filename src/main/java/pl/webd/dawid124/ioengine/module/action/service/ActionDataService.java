package pl.webd.dawid124.ioengine.module.action.service;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.rest.IUiAction;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiActionRequest;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.EIoDriverType;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.model.zone.ZoneState;
import pl.webd.dawid124.ioengine.module.state.service.StateService;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ActionDataService {

    private final DeviceService deviceService;
    private final StateService stateService;

    public ActionDataService(DeviceService deviceService, StateService stateService) {
        this.deviceService = deviceService;
        this.stateService = stateService;
    }

    public List<IoAction> fromUiAction(List<IUiAction> actions) {
        return actions.stream()
                .map(a -> UiAction.toIoAction(a, deviceService.fetchDevice(a.getIoId())))
                .collect(Collectors.toList());
    }

    public List<IoAction> fromUiActionRequest(UiActionRequest actions) {
        Map<String, IDevice> devices = deviceService.fetchAll();
        SceneState sceneState = stateService.fetchScene(actions.getZoneId(), actions.getSceneId());

        List<IoAction> list = new ArrayList<>();
        actions.getActions().forEach(a -> {
            sceneState.findStateById(a.getIoId()).ifPresent(state -> {
                list.addAll(ActionDataFactory.buildActions(devices, state, actions.getLedChangeData(), a.getDelay()));
            });
        });

        return list;
    }

    public List<IoAction> fromSceneState(SceneState sceneState) {
        Map<String, IDevice> devices = deviceService.fetchAll();

        return ActionDataFactory.buildActionsForFullScene(devices, sceneState);
    }

    public List<IoAction> fromDriverState(String driverId) {
        Map<String, IDevice> devices = deviceService.fetchAll();
        Map<String, ZoneState> zones = stateService.getZoneState();

        List<IoAction> appActions = ActionDataFactory.buildActionsForHome(devices, zones);

        return appActions.stream().filter(a -> a.getDeviceId().equals(driverId)).collect(Collectors.toList());
    }

}
