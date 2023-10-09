package pl.webd.dawid124.ioengine.module.automation;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.rest.EActionType;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.action.service.ActionService;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.MqttTemperatureSensorState;
import pl.webd.dawid124.ioengine.module.state.model.variable.BooleanVariable;
import pl.webd.dawid124.ioengine.module.state.model.zone.ZoneState;
import pl.webd.dawid124.ioengine.module.state.service.StateService;
import pl.webd.dawid124.ioengine.module.structure.model.TemperatureRange;
import pl.webd.dawid124.ioengine.module.structure.model.TemperatureScenes;
import pl.webd.dawid124.ioengine.module.structure.model.Zone;
import pl.webd.dawid124.ioengine.module.structure.service.StructureService;
import pl.webd.dawid124.ioengine.utils.TimeUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TemperatureService {

    public static final String ACTIVE_FLAG_KEY = "home-temperature-service-active";
    private final StructureService structureService;
    private final StateService stateService;
    private final DeviceService deviceService;
    private final ActionService actionService;

    private Map<String, Boolean> pumpActiveIds;

    public TemperatureService(StructureService structureService, StateService stateService, DeviceService deviceService,
                              ActionService actionService) {
        this.structureService = structureService;
        this.stateService = stateService;
        this.deviceService = deviceService;
        this.actionService = actionService;
        this.pumpActiveIds = new HashMap<>();
        this.structureService.fetchStructure().getVariables().put(ACTIVE_FLAG_KEY, new BooleanVariable(true));
    }

    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void run() {
        if (!isActive()) return;
        Map<String, Zone> zones = structureService.fetchStructure().getZones();
        Map<String, IDevice> devices = deviceService.fetchAll();

        this.pumpActiveIds = new HashMap<>();
        zones.values().stream()
                .filter(z -> z.getTemperature() != null && z.getTemperature().getPumpSwitchId() != null)
                .map(z -> z.getTemperature().getPumpSwitchId())
                .forEach(id -> this.pumpActiveIds.put(id, false));

        stateService.getZoneState().values().stream()
                .filter(z -> z.getTemperatureSceneStates() != null && !z.getTemperatureSceneStates().isEmpty())
                .forEach(zoneState -> processTemperature(zoneState, zones.get(zoneState.getId()), devices));

        processPumpSwitchAction();
    }

    private void processTemperature(ZoneState zoneState, Zone zoneStructure, Map<String, IDevice> devices) {
        TemperatureScenes temperatureScenes = zoneState.getTemperatureSceneStates().get(zoneState.getActiveTemperatureScene());
        List<String> sensors = zoneStructure.getTemperature().getSensors();

        double currentTemperature = calculateTemperature(devices, sensors);

        temperatureScenes.getRanges().stream()
                .filter(range -> range.isAllTime() || TimeUtils.isInRange(range.getHourFrom(), range.getHourTo()))
                .findFirst()
                .ifPresent(range -> processSwitchAction(zoneStructure, currentTemperature, range));
    }

    private void processSwitchAction(Zone zoneStructure, double currentTemperature, TemperatureRange range) {
        UiAction action = new UiAction();
        action.setIoId(zoneStructure.getTemperature().getSwitchId());

        String pumpSwitchId = zoneStructure.getTemperature().getPumpSwitchId();

        if (currentTemperature > range.getTemperature()) {
            action.setAction(EActionType.OFF);
            action.setZigbeeAction("OFF");
        } else {
            action.setAction(EActionType.ON);
            action.setZigbeeAction("ON");
            pumpActiveIds.put(pumpSwitchId, true);
        }

        actionService.processSimpleActions(Collections.singletonList(action));
    }

    private void processPumpSwitchAction() {
        pumpActiveIds.forEach((id, val) -> {
            UiAction action = new UiAction();
            action.setIoId(id);
            if (val) {
                action.setAction(EActionType.ON);
                action.setZigbeeAction("ON");
            } else {
                action.setAction(EActionType.OFF);
                action.setZigbeeAction("OFF");
            }
            actionService.processSimpleActions(Collections.singletonList(action));
        });

    }

    private double calculateTemperature(Map<String, IDevice> devices, List<String> sensors) {
        double sumTemperature = 0;
        int sensorCount = 0;
        for (String id: sensors) {
            IDevice iDevice = devices.get(id);
            DeviceState deviceState = stateService.getSensors().get(id);
            if (iDevice != null || deviceState instanceof MqttTemperatureSensorState) {
                sensorCount++;
                sumTemperature += ((MqttTemperatureSensorState) deviceState).getTemperature();
            }
        }

        return sumTemperature / sensorCount;
    }

    private boolean isActive() {
        return (Boolean) this.structureService.fetchStructure().getVariables().get(ACTIVE_FLAG_KEY).getValue();
    }
}
