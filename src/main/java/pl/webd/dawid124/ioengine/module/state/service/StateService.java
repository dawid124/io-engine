package pl.webd.dawid124.ioengine.module.state.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pl.webd.dawid124.ioengine.module.action.model.VarChangeRequest;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiActionRequest;
import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.module.state.model.device.*;
import pl.webd.dawid124.ioengine.module.state.model.rest.ZoneStateResponse;
import pl.webd.dawid124.ioengine.module.state.model.rest.ZonesStateResponse;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.model.zone.ZoneState;
import pl.webd.dawid124.ioengine.module.structure.model.Scene;
import pl.webd.dawid124.ioengine.module.structure.model.Zone;
import pl.webd.dawid124.ioengine.module.structure.service.StructureService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class StateService {

    private final StructureService structureService;

    private final Map<String, IVariable> variables;
    private final Map<String, ZoneState> zoneState;
    private final Map<String, DeviceState> sensors;

    public StateService(StructureService structureService) {
        this.structureService = structureService;

        variables = new HashMap<>();
        zoneState = new HashMap<>();
        sensors = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        createInitState();
    }

    public void createInitState() {
        for (Zone zoneStructure : structureService.fetchStructure().getZones().values()) {
            String firstScene = zoneStructure.getScenes().values().stream().filter(s -> s.getOrder() == 0).findFirst()
                    .map(Scene::getId).orElse("auto");

            ZoneState zone = new ZoneState(zoneStructure.getId(), zoneStructure.getName(), firstScene);
            addZoneState(zone);

            for (Scene sceneStructure : zoneStructure.getScenes().values()) {
                SceneState scene = new SceneState(sceneStructure.getId(), sceneStructure.getName());
                zone.addSceneState(scene);
                sceneStructure.getGroups().forEach(scene::addGroupState);
            }

            sensors.putAll(zoneStructure.getSensors());
        }
    }

    public void resetScene(String zoneId, String sceneId) {
        Scene sceneStructure = structureService.fetchScene(zoneId, sceneId);
        SceneState scene = zoneState.get(zoneId).getSceneStates().get(sceneId);
        scene.setGroupState(new HashMap<>());
        sceneStructure.getGroups().forEach(scene::addGroupState);
    }

    private void addZoneState(ZoneState state) {
        zoneState.put(state.getId(), state);
    }

    public SceneState fetchScene(String zoneId, String sceneId) {
        return zoneState.get(zoneId).getSceneStates().get(sceneId);
    }

    public SceneState fetchActiveScene(String zoneId) {
        ZoneState zoneState = this.zoneState.get(zoneId);
        return zoneState.getSceneStates().get(zoneState.getActiveScene());
    }

    public ZonesStateResponse fetchZoneStatesResponse() {
        ZonesStateResponse zones = new ZonesStateResponse();

        zoneState.values().forEach(z -> {
            SceneState scene = z.getSceneStates().get(z.getActiveScene());
            ZoneStateResponse zone =
                    new ZoneStateResponse(z.getId(), z.getActiveScene(), scene.getGroupState(), z.getVariables());
            zones.getZones().put(z.getId(), zone);
        });

        zones.setVariables(variables);

        return zones;
    }

    public void updateSateByUiAction(UiActionRequest actionReq) {
        SceneState sceneState = zoneState.get(actionReq.getZoneId()).getSceneStates().get(actionReq.getSceneId());

        for (UiAction action : actionReq.getActions()) {
            GroupState groupState = sceneState.findStateById(action.getIoId());

            updateSateByUiAction(groupState, action);
        }
    }

    public Map<String, ZoneState> getZoneState() {
        return zoneState;
    }

    public Map<String, IVariable> getVariables() {
        return variables;
    }

    public void updateSateByUiAction(GroupState item, UiAction action) {

        if (CollectionUtils.isEmpty(item.getChildren())) {
            updateDeviceSateByUiAction(action, item.getState());
        } else {
            updateGroupSateByUiAction(action, item);
        }

    }

    private void updateGroupSateByUiAction(UiAction ioAction, GroupState item) {
        boolean onlyBrightness = false;
        if (item.getState() instanceof ColorLedDeviceState) {
            ColorLedDeviceState lightState = (ColorLedDeviceState) item.getState();

            if (ioAction.getColor() == null || lightState.getColor().equals(ioAction.getColor())) {
                lightState.setBrightness(ioAction.getBrightness());
                onlyBrightness = true;

            } else {
                if (ioAction.getColor() != null) lightState.getColor().update(ioAction.getColor());
            }

        } else if (item.getState() instanceof NeoDeviceState) {
            NeoDeviceState neoState = (NeoDeviceState) item.getState();

            if ((ioAction.getColor() == null || neoState.getColor().equals(ioAction.getColor()))
                    && neoState.getAnimationId() == ioAction.getAnimationId()
                    && neoState.getSpeed() == ioAction.getSpeed()) {

                neoState.setBrightness(ioAction.getBrightness());
                onlyBrightness = true;

            } else {
                if (ioAction.getColor() != null) neoState.getColor().update(ioAction.getColor());
                neoState.setAnimationId(ioAction.getAnimationId());
                neoState.setSpeed(ioAction.getSpeed());
            }

        }

        if (!onlyBrightness) {
            for (GroupState child : item.getChildren()) {
                updateSateByUiAction(child, ioAction);
            }
        }
    }

    private void updateDeviceSateByUiAction(UiAction a, DeviceState state) {
        if (state instanceof ColorLedDeviceState) {
            updateColorLedState((ColorLedDeviceState) state, a);
        } else if (state instanceof NeoDeviceState) {
            updateNeoState((NeoDeviceState) state, a);
        } else if (state instanceof LedDeviceState) {
            updateLedState((LedDeviceState) state, a);
        }
    }

    private void updateLedState(LedDeviceState state, UiAction ioAction) {
        state.setBrightness(ioAction.getBrightness());
    }

    private void updateColorLedState(ColorLedDeviceState state, UiAction ioAction) {
        if (ioAction.getColor() == null || state.getColor().equals(ioAction.getColor())) {
            state.setBrightness(ioAction.getBrightness());
        } else if (ioAction.getColor() != null) {
            state.getColor().update(ioAction.getColor());
        }
    }

    private void updateNeoState(NeoDeviceState state, UiAction ioAction) {
        if ((ioAction.getColor() == null || state.getColor().equals(ioAction.getColor()))
                && state.getAnimationId() == ioAction.getAnimationId()
                && state.getSpeed() == ioAction.getSpeed()) {
            state.setBrightness(ioAction.getBrightness());
        } else {
            if (ioAction.getColor() != null) state.getColor().update(ioAction.getColor());
            state.setAnimationId(ioAction.getAnimationId());
            state.setSpeed(ioAction.getSpeed());
        }
    }

    public void updateScene(String zoneId, String sceneId) {

        ZoneState zone = this.zoneState.get(zoneId);
        if (zone != null) {
            zone.setActiveScene(sceneId);
        }
    }

    public void changeStateVar(VarChangeRequest change) {
        if (StringUtils.hasText(change.getZoneId())) {
            zoneState.values().stream()
                    .filter(z -> change.getZoneId().equals(z.getId()))
                    .findFirst()
                    .ifPresent(z -> z.getVariables().put(change.getName(), change.getValue()));
        } else {
            this.variables.put(change.getName(), change.getValue());
        }
    }

    public Map<String, DeviceState> getSensors() {
        return sensors;
    }
}
