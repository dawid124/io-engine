package pl.webd.dawid124.ioengine.service;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.home.state.device.ColorLedDeviceState;
import pl.webd.dawid124.ioengine.home.state.device.DeviceState;
import pl.webd.dawid124.ioengine.home.state.device.LedDeviceState;
import pl.webd.dawid124.ioengine.home.state.device.NeoDeviceState;
import pl.webd.dawid124.ioengine.home.state.scene.SceneState;
import pl.webd.dawid124.ioengine.home.state.variable.IVariable;
import pl.webd.dawid124.ioengine.home.state.zone.ZoneState;
import pl.webd.dawid124.ioengine.home.structure.Scene;
import pl.webd.dawid124.ioengine.home.structure.Zone;
import pl.webd.dawid124.ioengine.home.structure.group.LightGroup;
import pl.webd.dawid124.ioengine.model.ActionRequest;
import pl.webd.dawid124.ioengine.model.IoAction;
import pl.webd.dawid124.ioengine.model.ZoneResponse;
import pl.webd.dawid124.ioengine.model.ZonesResponse;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class StateService {

    private final DeviceService deviceService;
    private final StructureService structureService;

    private final Map<String, IVariable> variables;
    private final Map<String, ZoneState> zoneState;

    public StateService(DeviceService deviceService, StructureService structureService) {
        this.deviceService = deviceService;
        this.structureService = structureService;

        variables = new HashMap<>();
        zoneState = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        createInitState();
        System.out.println(zoneState);
    }

    public void createInitState() {
        for (Zone zoneStructure: structureService.fetchStructure().getZones().values()) {
            String firstScene = zoneStructure.getScenes().values().stream().filter(s -> s.getOrder() == 0).findFirst().map(Scene::getId).orElse("none");
            ZoneState zone = new ZoneState(zoneStructure.getId(), zoneStructure.getName(), firstScene);
            addZoneState(zone);

            for (Scene sceneStructure: zoneStructure.getScenes().values()) {
                SceneState scene = new SceneState(sceneStructure.getId(), sceneStructure.getName());
                zone.addSceneState(scene);

                for (LightGroup groupStructure: sceneStructure.getGroups()) {
                    scene.addGroupState(groupStructure.getInitialState());
                }

                for (DeviceState deviceState: sceneStructure.getLightsState()) {
                    scene.addDeviceState(deviceState.clone());
                }
            }
        }
    }

    private void addZoneState(ZoneState state) {
        zoneState.put(state.getId(), state);
    }


//    public Map<String, DeviceState> fetchAll() {
//        return this.deviceState;
//    }

//    public Map<String, DeviceState> fetchSelected(List<String> ids) {
//        return this.deviceState.entrySet().stream()
//                .filter(map -> ids.contains(map.getKey()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//    }

    public ZonesResponse fetchZones() {
        ZonesResponse zones = new ZonesResponse();

        zoneState.values().forEach(z -> zones.getZones().put(z.getId(), new ZoneResponse(z.getActiveScene())));

        return zones;
    }

    public void updateDeviceSate(ActionRequest action) {
        ZoneState zone = this.zoneState.get(action.getZoneId());
        for (IoAction a : action.getActions()) {
            DeviceState state = zone.findActiveScene().getDeviceState().get(a.getIoId());

            if (state instanceof LedDeviceState) {
                updateLedState((LedDeviceState) state, a);
            } else if (state instanceof ColorLedDeviceState) {
                updateColorLedState((ColorLedDeviceState) state, a);
            } else if (state instanceof NeoDeviceState) {
                updateNeoState((NeoDeviceState) state, a);
            }
        }
    }

    private void updateLedState(LedDeviceState state, IoAction ioAction) {
        state.setBrightness(ioAction.getBrightness());
    }

    private void updateColorLedState(ColorLedDeviceState state, IoAction ioAction) {
        state.setBrightness(ioAction.getBrightness());
        state.setColor(ioAction.getColor());
    }

    private void updateNeoState(NeoDeviceState state, IoAction ioAction) {
        state.setBrightness(ioAction.getBrightness());
        state.setColor(ioAction.getColor());
        state.setAnimationId(ioAction.getAnimationId());
        state.setSpeed(ioAction.getSpeed());
    }

    public void updateScene(String zoneId, String sceneId) {

        ZoneState zone = this.zoneState.get(zoneId);
        if (zone != null) {
            zone.setActiveScene(sceneId);
        }
    }
}
