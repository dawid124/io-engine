package pl.webd.dawid124.ioengine.mqtt;

import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.state.model.device.ColorLedDeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.LedDeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.NeoDeviceState;
import pl.webd.dawid124.ioengine.module.action.model.rest.EActionType;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;

public final class IoActionFactory {

    private IoActionFactory() {}

    public static IoAction fromDeviceState(IDevice device, DeviceState state) {
        IoAction a = new IoAction();
        a.setIoId(device.getId());
        a.setIoType(device.getIoType());
        a.setAction(EActionType.CHANGE);
        a.setDeviceId(device.getDriverConfiguration().getDriver().getId());
        a.setDeviceType(device.getDriverConfiguration().getDriver().getType());

        if (state instanceof NeoDeviceState) {
            NeoDeviceState neoState = (NeoDeviceState) state;

            a.setSpeed(neoState.getSpeed());
            a.setAnimationId(neoState.getAnimationId());
            a.setColor(neoState.getColor());
            a.setBrightness(neoState.getBrightness());
        } else if (state instanceof ColorLedDeviceState) {
            ColorLedDeviceState colorState = (ColorLedDeviceState) state;

            a.setColor(colorState.getColor());
            a.setBrightness(colorState.getBrightness());
        } else if (state instanceof LedDeviceState) {
            a.setBrightness(((LedDeviceState) state).getBrightness());
        }

        a.setTime(1000);
        return a;
    }
}
