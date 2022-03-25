package pl.webd.dawid124.ioengine.service;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.home.state.device.ColorLedDeviceState;
import pl.webd.dawid124.ioengine.home.state.device.DeviceState;
import pl.webd.dawid124.ioengine.home.state.device.LedDeviceState;
import pl.webd.dawid124.ioengine.home.state.device.NeoDeviceState;
import pl.webd.dawid124.ioengine.model.Action;
import pl.webd.dawid124.ioengine.model.ActionRequest;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StateService {

    private final DeviceService deviceService;

    private final Map<String, DeviceState> deviceState;

    public StateService(DeviceService deviceService) {
        this.deviceService = deviceService;

        deviceState = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        deviceService.fetchAll().values().forEach(device -> addDeviceState(device.getInitialState()));
    }

    private void addDeviceState(DeviceState state) {
        deviceState.put(state.getId(), state);
    }

    public Map<String, DeviceState> fetchAll() {
        return this.deviceState;
    }

    public Map<String, DeviceState> fetchSelected(List<String> ids) {
        return this.deviceState.entrySet().stream()
                .filter(map -> ids.contains(map.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void updateSate(ActionRequest action) {
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
}
