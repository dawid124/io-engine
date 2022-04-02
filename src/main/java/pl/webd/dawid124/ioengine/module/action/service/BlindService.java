package pl.webd.dawid124.ioengine.module.action.service;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.server.ServerUiAction;
import pl.webd.dawid124.ioengine.module.device.model.input.BlindDevice;
import pl.webd.dawid124.ioengine.module.state.model.device.EBlindDirection;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;

import java.util.List;

@Service
public class BlindService {

    public void processBlinds(List<ServerUiAction> actions) {
        for (ServerUiAction action : actions) {
            BlindDevice device = (BlindDevice) action.getDevice().getDevice();
            if (device == null) {
                return;
            }

            UiAction ioAction = action.getIoAction();
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
