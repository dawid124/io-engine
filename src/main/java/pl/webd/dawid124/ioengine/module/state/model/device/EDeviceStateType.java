package pl.webd.dawid124.ioengine.module.state.model.device;

public enum EDeviceStateType {
    BLIND(BlindDeviceState.class),
    NEO(NeoDeviceState.class),
    LED(LedDeviceState.class),
    RGBW(ColorLedDeviceState.class),
    MOTION_SENSOR(ColorLedDeviceState.class);

    Class clazz;

    EDeviceStateType(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }
}
