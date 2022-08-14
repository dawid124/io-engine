package pl.webd.dawid124.ioengine.module.action.service;

import org.springframework.util.CollectionUtils;
import pl.webd.dawid124.ioengine.module.action.model.server.LedChangeData;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.GroupState;
import pl.webd.dawid124.ioengine.module.state.model.device.LedDeviceState;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.model.zone.ZoneState;
import pl.webd.dawid124.ioengine.mqtt.IoActionFactory;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

final class ActionDataFactory {

    public static final int MAX_BRIGHTNESS = 255;
    private static final int FULL_PERCENT = 1;

    public static List<IoAction> buildActionsForHome(Map<String, IDevice> devices, Map<String, ZoneState> zones) {
        List<IoAction> fullHomeActions = new ArrayList<>();
        zones.values().forEach(zone -> {
            SceneState sceneState = zone.getSceneStates().get(zone.getActiveScene());
            fullHomeActions.addAll(buildActionsForFullScene(devices, sceneState));
        });

        return fullHomeActions;
    }

    public static List<IoAction> buildActionsForFullScene(Map<String, IDevice> devices, SceneState sceneState) {
        List<IoAction> actions = new ArrayList<>();
        for (GroupState groupState: sceneState.getGroupState().values()) {
            LedDeviceState state = (LedDeviceState) groupState.getState();
            double brightnessPercent = (double) state.getBrightness() / (double) MAX_BRIGHTNESS;
            actions.addAll(buildActions(devices, groupState, new LedChangeData(), 0, brightnessPercent));
        }

        return actions;
    }

    public static List<IoAction> buildActions(Map<String, IDevice> devices, GroupState item, LedChangeData ledChange, int delay) {
        return buildActions(devices, item, ledChange, delay, FULL_PERCENT);
    }

    public static List<IoAction> buildActions(Map<String, IDevice> devices, GroupState item, LedChangeData ledChange, int delay, double brightnessPercent) {
        List<GroupState> children = item.getChildren();
        List<IoAction> actions = new ArrayList<>();

        if (CollectionUtils.isEmpty(children)) {
            DeviceState deviceState = item.getState();
            IDevice device = devices.get(deviceState.getIoId());
            actions.add(IoActionFactory.fromDeviceState(device, deviceState, ledChange, delay, brightnessPercent));
        } else {
            LedDeviceState deviceState = (LedDeviceState) item.getState();
            double newBrightnessPercent =
                    brightnessPercent * ((double) deviceState.getBrightness() / (double) MAX_BRIGHTNESS);

            children.forEach(child -> {
                List<IoAction> a = buildActions(devices, child, ledChange, delay, newBrightnessPercent);
                actions.addAll(a);
            });
        }

        return actions;
    }
}
