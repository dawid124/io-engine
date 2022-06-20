package pl.webd.dawid124.ioengine.mqtt;

import pl.webd.dawid124.ioengine.module.action.model.rest.EActionType;
import pl.webd.dawid124.ioengine.module.action.model.server.LedChangeData;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.state.model.device.ColorLedDeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.LedDeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.NeoDeviceState;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;

public final class IoActionFactory {

    private IoActionFactory() {}

    public static IoAction fromDeviceState(IDevice device, DeviceState state, double brightnessPercent) {
        return fromDeviceState(device, state, new LedChangeData(), brightnessPercent);
    }

    public static IoAction fromDeviceState(IDevice device, DeviceState state, LedChangeData ledChange, double brightnessPercent) {
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
            a.setStaticSubModeId(1);
            divideBrightnessByGroup(a, brightnessPercent);
        } else if (state instanceof ColorLedDeviceState) {
            ColorLedDeviceState colorState = (ColorLedDeviceState) state;

            a.setColor(colorState.getColor());
            a.setBrightness(colorState.getBrightness());
            divideBrightnessByGroup(a, brightnessPercent);
        } else if (state instanceof LedDeviceState) {
            a.setBrightness(((LedDeviceState) state).getBrightness());

            divideBrightnessByGroup(a, brightnessPercent);
        }

        a.setTime(ledChange.getTime());
        a.setStepTime(ledChange.getStepTime());
        return a;
    }

    private static void divideBrightnessByGroup(IoAction action, double percent) {
        double newValue = action.getBrightness() * percent;
        action.setBrightness((int) newValue);
    }
}
