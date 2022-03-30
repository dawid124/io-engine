package pl.webd.dawid124.ioengine.rest.blind;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.home.devices.input.BlindDevice;
import pl.webd.dawid124.ioengine.home.devices.output.IDevice;
import pl.webd.dawid124.ioengine.home.state.device.EBlindDirection;
import pl.webd.dawid124.ioengine.model.IoAction;
import pl.webd.dawid124.ioengine.service.DeviceService;

import java.util.List;
import java.util.Map;

@Service
public class BlindService {

    private DeviceService deviceService;

    public BlindService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public void processBlinds(List<IoAction> ioActions) {
        Map<String, IDevice> devices = deviceService.fetchAll();

        for (IoAction ioAction : ioActions) {
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
