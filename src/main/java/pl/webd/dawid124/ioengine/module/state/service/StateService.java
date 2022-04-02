package pl.webd.dawid124.ioengine.module.state.service;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.state.model.device.ColorLedDeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.LedDeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.NeoDeviceState;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.model.zone.ZoneState;
import pl.webd.dawid124.ioengine.module.structure.model.Scene;
import pl.webd.dawid124.ioengine.module.structure.model.Zone;
import pl.webd.dawid124.ioengine.module.structure.model.LightGroup;
import pl.webd.dawid124.ioengine.module.action.model.IoAction;
import pl.webd.dawid124.ioengine.module.state.model.rest.ZoneStateResponse;
import pl.webd.dawid124.ioengine.module.state.model.rest.ZonesStateResponse;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.module.structure.service.StructureService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
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
    }

    public void createInitState() {
        for (Zone zoneStructure: structureService.fetchStructure().getZones().values()) {
            String firstScene = zoneStructure.getScenes().values().stream().filter(s -> s.getOrder() == 0).findFirst().map(Scene::getId).orElse("auto");
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

    public SceneState fetchScene(String zoneId, String sceneId) {
        return zoneState.get(zoneId).getSceneStates().get(sceneId);
    }

    public ZonesStateResponse fetchZoneStates() {
        ZonesStateResponse zones = new ZonesStateResponse();

        zoneState.values().forEach(z -> {
            SceneState scene = z.getSceneStates().get(z.getActiveScene());
            ZoneStateResponse zone = new ZoneStateResponse(z.getId(), z.getActiveScene(), scene.getDeviceState(), scene.getGroupState());
            zones.getZones().put(z.getId(), zone);
        });

        return zones;
    }

    public void updateGroupSate(List<IoAction> actions, LightGroup group, String zoneId, String sceneId) {
        SceneState scene = this.zoneState.get(zoneId).getSceneStates().get(sceneId);

        for (IoAction action: actions) {
            updateStateByType(action, scene.getGroupState().get(action.getIoId()));

            for (String ioId: group.getDeviceIds()) {
                updateStateByGroup(action, scene.getDeviceState().get(ioId));
            }
        }
    }

    public void updateDeviceSate(List<IoAction> actions, String zoneId, String sceneId) {
        SceneState scene = this.zoneState.get(zoneId).getSceneStates().get(sceneId);
        for (IoAction a : actions) {
            DeviceState state = scene.getDeviceState().get(a.getIoId());

            updateStateByType(a, state);
        }
    }

    private void updateStateByGroup(IoAction ioAction, DeviceState state) {
        if (state instanceof ColorLedDeviceState) {
            ((ColorLedDeviceState) state).setColor(ioAction.getColor());
        } else if (state instanceof NeoDeviceState) {
            NeoDeviceState neoState = (NeoDeviceState) state;
            neoState.setColor(ioAction.getColor());
            neoState.setAnimationId(ioAction.getAnimationId());
            neoState.setSpeed(ioAction.getSpeed());
        }
    }

    private void updateStateByType(IoAction a, DeviceState state) {
        if (state instanceof ColorLedDeviceState) {
            updateColorLedState((ColorLedDeviceState) state, a);
        } else if (state instanceof NeoDeviceState) {
            updateNeoState((NeoDeviceState) state, a);
        } else if (state instanceof LedDeviceState) {
            updateLedState((LedDeviceState) state, a);
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

    public DeviceState fetchGroupState(String zoneId, String sceneId, String ioId) {
       return fetchScene(zoneId, sceneId).getGroupState().get(ioId);
    }
}
