package pl.webd.dawid124.ioengine.module.state.service;

import io.jsondb.JsonDBTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pl.webd.dawid124.ioengine.database.Jsondb;
import pl.webd.dawid124.ioengine.module.action.model.VarChangeRequest;
import pl.webd.dawid124.ioengine.module.action.model.rest.EActionType;
import pl.webd.dawid124.ioengine.module.action.model.rest.IUiAction;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiActionRequest;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.module.state.model.StateVariable;
import pl.webd.dawid124.ioengine.module.state.model.device.*;
import pl.webd.dawid124.ioengine.module.state.model.rest.ZoneStateResponse;
import pl.webd.dawid124.ioengine.module.state.model.rest.ZonesStateResponse;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.model.zone.ZoneState;
import pl.webd.dawid124.ioengine.module.structure.model.Scene;
import pl.webd.dawid124.ioengine.module.structure.model.TemperatureScenes;
import pl.webd.dawid124.ioengine.module.structure.model.Zone;
import pl.webd.dawid124.ioengine.module.structure.service.StructureService;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class StateService {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final StructureService structureService;
    private final DeviceService deviceService;
    private final JsonDBTemplate db;

    private final Map<String, StateVariable> variables;
    private final Map<String, ZoneState> zoneState;
    private final Map<String, DeviceState> sensors;



    public StateService(StructureService structureService, DeviceService deviceService, Jsondb db) {
        this.structureService = structureService;
        this.deviceService = deviceService;
        this.db = db.instance();

        variables = new HashMap<>();
        zoneState = new LinkedHashMap<>();
        sensors = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        if (!db.collectionExists(ZoneState.class)) {
            db.createCollection(ZoneState.class);
        }
        if (!db.collectionExists(DeviceState.class)) {
            db.createCollection(DeviceState.class);
        }
        if (!db.collectionExists(StateVariable.class)) {
            db.createCollection(StateVariable.class);
        }
        createInitState();
    }

    public void createInitState() {
        for (Zone zoneStructure : structureService.fetchStructure().getZones().values()) {

            ZoneState zone = db.findById(zoneStructure.getId(), ZoneState.class);

            if (zone == null) {
                zone = createInitialZone(zoneStructure);
                db.insert(zone);
            }

            addZoneState(zone);
        }

       deviceService.fetchAll().forEach((id, device) -> {
           DeviceState state = db.findById(id, DeviceState.class);
           if (state == null) {
               state = device.getInitialState();
               db.insert(state);
           }
           sensors.put(id, state);
       });

        structureService.fetchStructure().getVariables().forEach((k, v) -> {
            StateVariable variable = db.findById(k, StateVariable.class);
            if (variable == null) {
                variable = new StateVariable(k, v);
                db.insert(variable);
            }
        });

        db.findAll(StateVariable.class).forEach(v -> {
            variables.put(v.getId(), v);
        });
    }

    public void updateDbZone(ZoneState instance) {
        db.save(instance, ZoneState.class);
    }

    private void updateDbVariable(StateVariable instance) {
        try {
            db.insert(instance);
        } catch (Exception ex) {
            db.save(instance, StateVariable.class);
        }
    }

    private void updateDbDeviceState(DeviceState instance) {
        db.save(instance, DeviceState.class);
    }

    private ZoneState createInitialZone(Zone zoneStructure) {
        String firstScene = zoneStructure.getScenes().values().stream().filter(s -> s.getOrder() == 0).findFirst()
                .map(Scene::getId).orElse("auto");

        String firstTemperatureScene = null;
        Map<String, TemperatureScenes> temperatureScenes = new HashMap<>();
        if (zoneStructure.getTemperature() != null) {
            temperatureScenes = zoneStructure.getTemperature().getScenes();
            firstTemperatureScene = temperatureScenes.keySet().iterator().next();
        }

        ZoneState zone = new ZoneState(zoneStructure.getId(), zoneStructure.getName(), firstScene, firstTemperatureScene);

        for (Scene sceneStructure : zoneStructure.getScenes().values()) {
            SceneState scene = new SceneState(sceneStructure.getId(), sceneStructure.getName());
            zone.addSceneState(scene);
            sceneStructure.getGroups().forEach(scene::addGroupState);
        }

        zone.getTemperatureSceneStates().putAll(temperatureScenes);

        return zone;
    }

    public void resetScene(String zoneId, String sceneId) {
        Scene sceneStructure = structureService.fetchScene(zoneId, sceneId);
        SceneState scene = zoneState.get(zoneId).getSceneStates().get(sceneId);
        scene.setGroupState(new LinkedHashMap<>());
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
                    new ZoneStateResponse(
                            z.getId(), z.getActiveScene(),
                            z.getActiveTemperatureScene(),
                            scene.getGroupState(),
                            z.getTemperatureSceneStates(),
                            z.getVariables());
            zones.getZones().put(z.getId(), zone);
        });

        zones.setVariables(variables);
        zones.setSensors(sensors);

        return zones;
    }

    public void updateSateByUiAction(UiActionRequest actionReq) {
        SceneState sceneState = zoneState.get(actionReq.getZoneId()).getSceneStates().get(actionReq.getSceneId());

        for (IUiAction action : actionReq.getActions()) {
            sceneState.findStateById(action.getIoId()).ifPresent(state -> updateSateByUiAction(actionReq.getZoneId(), state, action));
        }

        updateDbZone(zoneState.get(actionReq.getZoneId()));
    }

    public Map<String, ZoneState> getZoneState() {
        return zoneState;
    }

    public Map<String, StateVariable> getVariables() {
        return variables;
    }

    public void updateVariable(String id, StateVariable var) {
        variables.put(id, var);
        updateDbVariable(var);
    }

    public void updateSateByUiAction(String zoneId, GroupState item, IUiAction action) {

        if (CollectionUtils.isEmpty(item.getChildren())) {
            updateDeviceSateByUiAction(action, item.getState());
        } else {
            updateGroupSateByUiAction(zoneId, action, item);
        }

        updateDbZone(zoneState.get(zoneId));
    }

    private void updateGroupSateByUiAction(String zoneId, IUiAction ioAction, GroupState item) {
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
                updateSateByUiAction(zoneId, child, ioAction);
            }
        }
    }

    private void updateDeviceSateByUiAction(IUiAction a, DeviceState state) {
        if (state instanceof ColorLedDeviceState) {
            updateColorLedState((ColorLedDeviceState) state, a);
        } else if (state instanceof NeoDeviceState) {
            updateNeoState((NeoDeviceState) state, a);
        } else if (state instanceof LedDeviceState) {
            updateLedState((LedDeviceState) state, a);
        }
    }

    private void updateLedState(LedDeviceState state, IUiAction ioAction) {
        state.setBrightness(ioAction.getBrightness());
    }

    private void updateColorLedState(ColorLedDeviceState state, IUiAction ioAction) {
        if (ioAction.getColor() == null || state.getColor().equals(ioAction.getColor())) {
            state.setBrightness(ioAction.getBrightness());
        } else if (ioAction.getColor() != null) {
            state.setBrightness(ioAction.getBrightness());
            state.getColor().update(ioAction.getColor());
        }
    }

    private void updateNeoState(NeoDeviceState state, IUiAction ioAction) {
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

        updateDbZone(zone);
    }

    public void changeStateVar(VarChangeRequest change) {
        if (StringUtils.hasText(change.getZoneId())) {
            zoneState.values().stream()
                    .filter(z -> change.getZoneId().equals(z.getId()))
                    .findFirst()
                    .ifPresent(z -> z.getVariables().put(change.getName(), change.getValue()));
        } else {
            this.variables.put(change.getName(), new StateVariable(change.getName(), change.getValue()));
        }
    }

    public Map<String, DeviceState> getSensors() {
        return sensors;
    }

    public void updateDeviceState(String id, DeviceState state) {
        sensors.put(id, state);
        updateDbDeviceState(state);
    }

    public void updateDeviceStates(List<IoAction> mqttActions) {
        // temp implementation
        mqttActions.forEach(a -> {
            DeviceState state = sensors.get(a.getIoId());
            if (EDeviceStateType.SWITCH.equals(state.getIoType())) {
                SwitchDeviceState switchState = (SwitchDeviceState) state;
                if (EActionType.ON.equals(a.getAction())) {
                    switchState.setOn(true);
                } else if (EActionType.OFF.equals(a.getAction())) {
                    switchState.setOn(false);
                } else if (EActionType.TEMP_ON.equals(a.getAction())) {
                    switchState.setOn(true);
                    scheduler.schedule(() -> {
                        switchState.setOn(false);
                        updateDbDeviceState(state);
                    }, a.getTime(), TimeUnit.MILLISECONDS);
                }
//                state.update(a);
                updateDbDeviceState(state);
            }
        });

    }
}
