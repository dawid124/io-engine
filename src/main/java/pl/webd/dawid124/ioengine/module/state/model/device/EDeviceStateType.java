package pl.webd.dawid124.ioengine.module.state.model.device;

public enum EDeviceStateType {
    SWITCH(SwitchDeviceState.class),
    BLIND(BlindDeviceState.class),
    NEO(NeoDeviceState.class),
    LED(LedDeviceState.class),
    CCT(ColorLedDeviceState.class),
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
