package pl.webd.dawid124.ioengine.module.action.service;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiActionRequest;
import pl.webd.dawid124.ioengine.module.action.model.server.ServerUiAction;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.service.StateService;
import pl.webd.dawid124.ioengine.module.structure.model.Scene;
import pl.webd.dawid124.ioengine.module.structure.model.Zone;
import pl.webd.dawid124.ioengine.module.structure.service.StructureService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ActionDataService {

    private final DeviceService deviceService;
    private final StateService stateService;
    private final StructureService structureService;

    public ActionDataService(DeviceService deviceService, StateService stateService, StructureService structureService) {
        this.deviceService = deviceService;
        this.stateService = stateService;
        this.structureService = structureService;
    }

    public List<ServerUiAction> fromUiActionRequest(UiActionRequest actions) {
        Map<String, IDevice> devices = deviceService.fetchAll();
        Scene sceneStructure = structureService.fetchScene(actions.getZoneId(), actions.getSceneId());
        SceneState sceneState = stateService.fetchScene(actions.getZoneId(), actions.getSceneId());

        return actions.getActions().stream()
                .map(a -> ActionDataFactory.init(a, devices, sceneStructure, sceneState).process())
                .collect(Collectors.toList());
    }

    public List<ServerUiAction> fromSceneState(String zoneId, String sceneId) {
        Map<String, IDevice> devices = deviceService.fetchAll();
        Scene sceneStructure = structureService.fetchScene(zoneId, sceneId);
        SceneState sceneState = stateService.fetchScene(zoneId, sceneId);

       return sceneState.getDeviceState().values().stream()
                .map(state -> ActionDataFactory.init(state.getIoId(), devices, sceneStructure, sceneState).process())
                .collect(Collectors.toList());
    }

    public List<ServerUiAction> fromDriverState(String driverId) {
        List<ServerUiAction> fullList = new ArrayList<>();

        Map<String, IDevice> devices = deviceService.fetchDevicesByDriverId(driverId);
        Map<String, Zone> zones = structureService.fetchStructure().getZones();

        for (Zone zone : zones.values()) {
            SceneState sceneState = stateService.fetchActiveScene(zone.getId());
            List<ServerUiAction> actions = sceneState.getDeviceState().values().stream()
                    .filter(deviceState -> devices.get(deviceState.getIoId()) != null)
                    .map(state -> {
                        Scene sceneStructure = zone.getScenes().get(sceneState.getId());
                        return ActionDataFactory.init(state.getIoId(), devices, sceneStructure, sceneState)
                                .process();
                    })
                    .collect(Collectors.toList());

            fullList.addAll(actions);
        }

        return fullList;
    }

}
