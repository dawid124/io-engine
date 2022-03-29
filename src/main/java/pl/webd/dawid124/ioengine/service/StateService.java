package pl.webd.dawid124.ioengine.service;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.home.state.device.ColorLedDeviceState;
import pl.webd.dawid124.ioengine.home.state.device.DeviceState;
import pl.webd.dawid124.ioengine.home.state.device.LedDeviceState;
import pl.webd.dawid124.ioengine.home.state.device.NeoDeviceState;
import pl.webd.dawid124.ioengine.home.state.variable.IVariable;
import pl.webd.dawid124.ioengine.home.state.zone.ZoneState;
import pl.webd.dawid124.ioengine.home.structure.Scene;
import pl.webd.dawid124.ioengine.model.Action;
import pl.webd.dawid124.ioengine.model.ActionRequest;
import pl.webd.dawid124.ioengine.model.ZoneResponse;
import pl.webd.dawid124.ioengine.model.ZonesResponse;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StateService {

    private final DeviceService deviceService;
    private final StructureService structureService;

    private final Map<String, DeviceState> deviceState;
    private final Map<String, IVariable> variables;
    private final Map<String, ZoneState> zoneState;

    public StateService(DeviceService deviceService, StructureService structureService) {
        this.deviceService = deviceService;
        this.structureService = structureService;

        deviceState = new HashMap<>();
        variables = new HashMap<>();
        zoneState = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        deviceService.fetchAll().values().forEach(device -> addDeviceState(device.getInitialState()));
        structureService.fetchStructure().getZones().values().forEach(z -> {
            String firstScene = z.getScenes().values().stream().filter(s -> s.getOrder() == 0).findFirst().map(Scene::getId).orElse(null);
            addZoneState(new ZoneState(z.getId(), z.getName(), firstScene));
        });
    }

    private void addDeviceState(DeviceState state) {
        deviceState.put(state.getId(), state);
    }

    private void addZoneState(ZoneState state) {
        zoneState.put(state.getId(), state);
    }


    public Map<String, DeviceState> fetchAll() {
        return this.deviceState;
    }

    public Map<String, DeviceState> fetchSelected(List<String> ids) {
        return this.deviceState.entrySet().stream()
                .filter(map -> ids.contains(map.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public ZonesResponse fetchZones() {
        ZonesResponse zones = new ZonesResponse();

        zoneState.values().forEach(z -> zones.getZones().put(z.getId(), new ZoneResponse(z.getActiveScene())));

        return zones;
    }

    public void updateDeviceSate(ActionRequest action) {
        for (Action a : action.getActions()) {
            DeviceState state = this.deviceState.get(a.getIoId());

            if (state instanceof LedDeviceState) {
                updateLedState((LedDeviceState) state, a);
            } else if (state instanceof ColorLedDeviceState) {
                updateColorLedState((ColorLedDeviceState) state, a);
            } else if (state instanceof NeoDeviceState) {
                updateNeoState((NeoDeviceState) state, a);
            }
        }
    }

    private void updateLedState(LedDeviceState state, Action action) {
        state.setBrightness(action.getBrightness());
    }

    private void updateColorLedState(ColorLedDeviceState state, Action action) {
        state.setBrightness(action.getBrightness());
        state.setColor(action.getColor());
    }

    private void updateNeoState(NeoDeviceState state, Action action) {
        state.setBrightness(action.getBrightness());
        state.setColor(action.getColor());
        state.setAnimationId(action.getAnimationId());
        state.setSpeed(action.getSpeed());
    }

    public void updateScene(String zoneId, String sceneId) {

        ZoneState zone = this.zoneState.get(zoneId);
        if (zone != null) {
            zone.setActiveScene(sceneId);
        }
    }
}
