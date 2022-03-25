package pl.webd.dawid124.ioengine.rest.blind;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.home.devices.output.BlindDevice;
import pl.webd.dawid124.ioengine.home.devices.output.IDevice;
import pl.webd.dawid124.ioengine.home.state.device.EBlindDirection;
import pl.webd.dawid124.ioengine.model.Action;
import pl.webd.dawid124.ioengine.service.DeviceService;

import java.util.List;
import java.util.Map;

@Service
public class BlindService {

    private DeviceService deviceService;

    public BlindService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public void processBlinds(List<Action> actions) {
        Map<String, IDevice> devices = deviceService.fetchAll();

        for (Action action: actions) {
            BlindDevice device = (BlindDevice) devices.get(action.getIoId());
            if (device == null) {
                return;
            }

            switch (action.getAction()) {
                case UP:
                    device.move(EBlindDirection.UP);
                    break;
                case DOWN:
                    device.move(EBlindDirection.DOWN);
                    break;
                case DIM_UP:
                case DIM_DOWN:
                    device.dim(EBlindDirection.DIMMER, action.getPercent());
                    break;
            }
        }
    }
}
