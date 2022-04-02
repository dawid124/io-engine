package pl.webd.dawid124.ioengine.module.action.service;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.EIoDriverType;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.LedDeviceState;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.structure.model.Scene;
import pl.webd.dawid124.ioengine.module.structure.model.LightGroup;
import pl.webd.dawid124.ioengine.module.action.model.ActionDevice;
import pl.webd.dawid124.ioengine.module.action.model.UiActionRequest;
import pl.webd.dawid124.ioengine.module.action.model.IoAction;
import pl.webd.dawid124.ioengine.mqtt.MqttService;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.module.state.service.StateService;
import pl.webd.dawid124.ioengine.module.structure.service.StructureService;
import pl.webd.dawid124.ioengine.utils.IoActionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserActionService {

    public static final int MAX_LED_VALUE = 255;
    private final StateService stateService;

    private final BlindService blindService;
    private final MqttService mqttService;

    private final DeviceService deviceService;
    private final StructureService structureService;

    public UserActionService(StateService stateService, BlindService blindService, MqttService mqttService, DeviceService deviceService,
            StructureService structureService) {
        this.mqttService = mqttService;
        this.stateService = stateService;
        this.blindService = blindService;
        this.deviceService = deviceService;
        this.structureService = structureService;
    }

    public SceneState processSceneChange(String zoneId, String sceneId) {
        stateService.updateScene(zoneId, sceneId);

        SceneState sceneState = stateService.fetchScene(zoneId, sceneId);

        List<IoAction> actionList = new ArrayList<>();

        for (DeviceState deviceState: sceneState.getDeviceState().values()) {
            IDevice device = deviceService.fetchDevice(deviceState.getIoId());
            if (device != null) {
                actionList.add(IoActionFactory.fromDeviceState(device, deviceState));
            }
        }

        processLights(actionList, zoneId, sceneId);

        return sceneState;
    }

    public UiActionRequest processActionChange(UiActionRequest action) {
        List<IoAction> groups = new ArrayList<>();
        List<IoAction> blinds = new ArrayList<>();
        List<IoAction> lights = new ArrayList<>();

        for (IoAction a: action.getActions()) {
            if (a.isGroup()) groups.add(a);
            else if (a.isBlind()) blinds.add(a);
            else lights.add(a);
        }

        processBlinds(blinds);
        processLightGroups(groups, action.getZoneId(), action.getSceneId());
        processLights(lights, action.getZoneId(), action.getSceneId());

        return action;
    }

    private void processLightGroups(List<IoAction> actions, String zoneId, String sceneId) {
        List<LightGroup> groups = structureService.fetchScene(zoneId, sceneId).getGroups();

        for (IoAction action: actions) {

            groups.stream()
                    .filter(g -> action.getIoId().equals(g.getIoId())).findFirst()
                    .ifPresent(g ->  {
                        stateService.updateGroupSate(actions, g, zoneId, sceneId);
                        processLightGroup(zoneId, sceneId, g);
                    });
        }
    }

    private void processLightGroup(String zoneId, String sceneId, LightGroup group) {
        SceneState sceneState = stateService.fetchScene(zoneId, sceneId);
        List<IoAction> actionList = new ArrayList<>();

        for (String ioId : group.getDeviceIds()) {
            DeviceState deviceState = sceneState.getDeviceState().get(ioId);
            IDevice device = deviceService.fetchDevice(ioId);
            if (device != null) {
                actionList.add(IoActionFactory.fromDeviceState(device, deviceState));
            }
        }

        processLights(actionList, zoneId, sceneId);
    }

    private void processLights(List<IoAction> actions, String zoneId, String sceneId) {
        stateService.updateDeviceSate(actions, zoneId, sceneId);

        Scene sceneStructure = structureService.fetchScene(zoneId, sceneId);

        Map<String, IDevice> devices = deviceService.fetchSelected(actions.stream().map(IoAction::getIoId).collect(Collectors.toList()));
        List<ActionDevice> actionDevices = actions.stream()
                .map(a -> {
                    IDevice device = devices.get(a.getIoId());
                    divideBrightnessByGroupValue(zoneId, sceneId, a, sceneStructure.getGroups());

                    return new ActionDevice(a, device);
                })
                .collect(Collectors.toList());

        List<ActionDevice> mqttDrivers = actionDevices.stream()
                .filter(a -> EIoDriverType.MQTT.equals(a.getDevice().getDriverConfiguration().getDriver().getType()))
                .collect(Collectors.toList());

        mqttService.sendActionsToDevices(mqttDrivers);
    }

    private void divideBrightnessByGroupValue(String zoneId, String sceneId, IoAction action, List<LightGroup> groups) {
        getGroupByIoId(groups, action.getIoId()).ifPresent(g -> {
            DeviceState groupState = stateService.fetchGroupState(zoneId, sceneId, g.getIoId());
            LedDeviceState ledDeviceState = (LedDeviceState) groupState;

            if (ledDeviceState.getBrightness() < MAX_LED_VALUE) {
                double percent = (double) ledDeviceState.getBrightness() / (double) MAX_LED_VALUE;
                double newValue = action.getBrightness() * percent;
                action.setBrightness((int) newValue);
            }
        });

    }

    private Optional<LightGroup> getGroupByIoId(List<LightGroup> groups, String ioId) {
       return groups.stream().filter(g -> g.getDeviceIds().contains(ioId)).findFirst();
    }

    private void processBlinds(List<IoAction> actions) {
        blindService.processBlinds(actions);
    }
}
