package pl.webd.dawid124.ioengine.module.automation;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.rest.EActionType;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.action.service.ActionService;
import pl.webd.dawid124.ioengine.module.automation.model.HeatingState;
import pl.webd.dawid124.ioengine.module.automation.model.ZoneHeatingMemory;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.module.state.model.StateVariable;
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

    // Temperature thresholds for cyclic algorithm
    private static final double TEMP_THRESHOLD_IDLE = 0.1;  // Threshold for IDLE state
    private static final double TEMP_THRESHOLD_CLOSE = 0.3; // Close to target, transition to IDLE

    private final StructureService structureService;
    private final StateService stateService;
    private final DeviceService deviceService;
    private final ActionService actionService;

    private Map<String, Boolean> pumpActiveIds;
    private Map<String, ZoneHeatingMemory> zoneMemories;

    public TemperatureService(StructureService structureService, StateService stateService, DeviceService deviceService,
                              ActionService actionService) {
        this.structureService = structureService;
        this.stateService = stateService;
        this.deviceService = deviceService;
        this.actionService = actionService;
        this.pumpActiveIds = new HashMap<>();
        this.zoneMemories = new HashMap<>();
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
                .ifPresent(range -> processSwitchActionCyclic(zoneStructure, currentTemperature, range));
    }

    /**
     * Cyclic temperature management algorithm with state memory
     * Prevents overheating due to thermal inertia of the system
     */
    private void processSwitchActionCyclic(Zone zoneStructure, double currentTemperature, TemperatureRange range) {
        String zoneId = zoneStructure.getId();
        String switchId = zoneStructure.getTemperature().getSwitchId();
        String pumpSwitchId = zoneStructure.getTemperature().getPumpSwitchId();
        double targetTemperature = range.getTemperature();

        // Initialize zone memory if it doesn't exist
        ZoneHeatingMemory memory = zoneMemories.computeIfAbsent(zoneId, k -> new ZoneHeatingMemory(zoneId));

        boolean shouldHeat = false;
        HeatingState currentState = memory.getState();

        // State machine for cyclic algorithm
        switch (currentState) {
            case IDLE:
                // Temperature dropped below target -> start heating+
                if (currentTemperature < targetTemperature) {
                    memory.startHeating(currentTemperature);
                    shouldHeat = true;
                }
                break;

            case HEATING:
                // Heat for 15 minutes
                shouldHeat = true;
                if (memory.isHeatingDurationElapsed()) {
                    // If temperature is still very low (more than 1.0 below target), restart heating immediately
                    if (currentTemperature < targetTemperature - 1.0) {
                        memory.startHeating(currentTemperature);
                    } else {
                        // Normal cyclic behavior: transition to WAITING
                        memory.startWaiting(currentTemperature);
                        shouldHeat = false;
                    }
                }
                break;

            case WAITING:
                // Wait and observe temperature rise
                shouldHeat = false;

                // If temperature is very low (more than 1.0 below target), start heating immediately
                if (currentTemperature < targetTemperature - 1.0) {
                    memory.startHeating(currentTemperature);
                    shouldHeat = true;
                    break;
                }

                // If temperature dropped during waiting -> heat immediately
                if (currentTemperature < memory.getTemperatureAtWaitingStart() - 0.1) {
                    memory.startHeating(currentTemperature);
                    shouldHeat = true;
                    break;
                }

                // If waiting time elapsed
                if (memory.isWaitingDurationElapsed()) {
                    double tempRise = currentTemperature - memory.getTemperatureAtWaitingStart();

                    // Calculate next waiting time based on rise
                    int nextWaitingDuration = memory.calculateNextWaitingDuration(tempRise);
                    memory.updateWaitingDuration(nextWaitingDuration);

                    // Decision: IDLE or next HEATING cycle
                    if (currentTemperature >= targetTemperature - TEMP_THRESHOLD_CLOSE) {
                        // Temperature close to target -> IDLE
                        memory.setIdle();
                    } else {
                        // Temperature still too low -> next heating cycle
                        memory.startHeating(currentTemperature);
                        shouldHeat = true;
                    }
                }
                break;
        }

        // Execute heating switch action
        UiAction action = new UiAction();
        action.setIoId(switchId);

        if (shouldHeat) {
            action.setAction(EActionType.ON);
            action.setZigbeeAction("ON");
            pumpActiveIds.put(pumpSwitchId, true);
        } else {
            action.setAction(EActionType.OFF);
            action.setZigbeeAction("OFF");
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
        StateVariable variable = this.stateService.getVariables().get(ACTIVE_FLAG_KEY);
        if (variable == null) return false;

        return BooleanVariable.getBooleanValue(variable.getVar());
    }
}
