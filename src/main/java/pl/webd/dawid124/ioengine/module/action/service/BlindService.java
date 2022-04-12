package pl.webd.dawid124.ioengine.module.action.service;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.server.ServerUiAction;
import pl.webd.dawid124.ioengine.module.device.model.input.BlindDevice;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.state.model.device.EBlindDirection;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.mqtt.IoAction;

import java.util.List;
import java.util.Map;

@Service
public class BlindService {
    private DeviceService deviceService;

    public BlindService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public void processBlinds(List<UiAction> ioActions) {
        Map<String, IDevice> devices = deviceService.fetchAll();

        for (UiAction ioAction : ioActions) {
            BlindDevice device = (BlindDevice) devices.get(ioAction.getIoId());
            if (device == null) {
                return;
            }

            switch (ioAction.getAction()) {
                case UP:
                    device.move(EBlindDirection.UP);
                    break;
                case DOWN:
                    device.move(EBlindDirection.DOWN);
                    break;
                case DIM_UP:
                    device.dim(EBlindDirection.UP, ioAction.getPercent());
                    break;
                case DIM_DOWN:
                    device.dim(EBlindDirection.DOWN, ioAction.getPercent());
                    break;
            }
        }
    }
}
