package pl.webd.dawid124.ioengine.module.action.service;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.rest.EActionType;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.EIoDriverType;
import pl.webd.dawid124.ioengine.module.device.model.output.BlindDevice;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.state.model.device.EBlindDirection;
import pl.webd.dawid124.ioengine.module.action.model.rest.IUiAction;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BlindService {
    private DeviceService deviceService;

    public BlindService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public List<IoAction> processBlinds(List<IUiAction> ioActions) {
        Map<String, IDevice> devices = deviceService.fetchAll();

        List<IoAction> mqttActions = new ArrayList<>();

        for (IUiAction ioAction : ioActions) {
            BlindDevice device = (BlindDevice) devices.get(ioAction.getIoId());
            if (device == null) {
                continue;
            }

            if (EIoDriverType.MQTT.equals(device.getDriverConfiguration().getDriver().getType())) {
                mqttActions.add(processMqtt(ioAction, device));
            } else if (EIoDriverType.LOCAL_IO.equals(device.getDriverConfiguration().getDriver().getType())) {
                processLocal(ioAction, device);
            }
        }

        return mqttActions;
    }

    private IoAction processMqtt(IUiAction ioAction, BlindDevice device) {
        IoAction a = new IoAction();
        a.setIoId(device.getId());
        a.setIoType(device.getIoType());
        a.setDeviceId(device.getDriverConfiguration().getDriver().getId());
        a.setDeviceType(device.getDriverConfiguration().getDriver().getType());

        switch (ioAction.getAction()) {
            case UP:
                a.setAction(EActionType.UP);
                a.setTime(BlindDevice.FULL_DIMMER_TIME);
                break;
            case DOWN:
                a.setAction(EActionType.DOWN);
                a.setTime(BlindDevice.FULL_DIMMER_TIME);
                break;
            case DIM_UP:
                a.setAction(EActionType.UP);
                a.setTime(getCalculatedPercent(ioAction.getPercent()));
                break;
            case DIM_DOWN:
                a.setAction(EActionType.DOWN);
                a.setTime(getCalculatedPercent(ioAction.getPercent()));
                break;
        }

        return a;
    }

    private int getCalculatedPercent(int percent) {
        return Math.min(BlindDevice.FULL_CHANGE_TIME, Math.round(BlindDevice.FULL_CHANGE_TIME / 100 * percent));
    }

    private void processLocal(IUiAction ioAction, BlindDevice device) {
        switch (ioAction.getAction()) {
            case UP:
                device.moveLocal(EBlindDirection.UP, BlindDevice.FULL_DIMMER_TIME);
                break;
            case DOWN:
                device.moveLocal(EBlindDirection.DOWN, BlindDevice.FULL_DIMMER_TIME);
                break;
            case DIM_UP:
                device.moveLocal(EBlindDirection.UP, getCalculatedPercent(ioAction.getPercent()));
                break;
            case DIM_DOWN:
                device.moveLocal(EBlindDirection.DOWN, getCalculatedPercent(ioAction.getPercent()));
                break;
        }
    }
}
