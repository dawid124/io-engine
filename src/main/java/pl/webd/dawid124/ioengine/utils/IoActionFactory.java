package pl.webd.dawid124.ioengine.utils;

import pl.webd.dawid124.ioengine.home.devices.output.IDevice;
import pl.webd.dawid124.ioengine.home.state.device.ColorLedDeviceState;
import pl.webd.dawid124.ioengine.home.state.device.DeviceState;
import pl.webd.dawid124.ioengine.home.state.device.LedDeviceState;
import pl.webd.dawid124.ioengine.home.state.device.NeoDeviceState;
import pl.webd.dawid124.ioengine.model.IoAction;

public final class IoActionFactory {

    private IoActionFactory() {}

    public static IoAction fromDeviceState(IDevice device, DeviceState state) {
        IoAction a = new IoAction();
        a.setIoId(device.getId());
        a.setIoType(device.getType());

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
