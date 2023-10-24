package pl.webd.dawid124.ioengine.module.state.model.device;

public enum EDeviceStateType {
    SWITCH(SwitchDeviceState.class),
    ZIGBEE_SWITCH(SwitchDeviceState.class),
    POWER_METER_SWITCH(ZigbeeSwitchDeviceState.class),
    BLIND(BlindDeviceState.class),
    NEO(NeoDeviceState.class),
    LED(LedDeviceState.class),
    CCT(ColorLedDeviceState.class),
    RGBW(ColorLedDeviceState.class),
    MOTION_SENSOR(MotionSensorState.class),
    MQTT_TEMPERATURE_SENSOR(MqttTemperatureSensorState.class),
    ZIGBEE_STATE_LESS(ZigbeeStateLessDeviceState.class);

    Class clazz;

    EDeviceStateType(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public boolean isMqtt() {
        return MQTT_TEMPERATURE_SENSOR.equals(this);
    }
}
