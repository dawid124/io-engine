package pl.webd.dawid124.ioengine.module.automation.macro.fetcher;

public enum EVariableFetcherType {

    MACRO_VARIABLE(MacroVariableFetcher.class),
    DEVICE_STATE(DeviceStateFetcher.class),
    GLOBAL_VARIABLE(GlobalVariableFetcher.class),
    ZONE_VARIABLE(null),
    CURRENT_STATE_VARIABLE(CurrentStateVariableFetcher.class),
    SENSOR_INACTIVE_FOR_TIME(SensorInactiveForTimeVariableFetcher.class),
    SENSOR_ACTIVE_IN_TIME(SensorActiveInTimeVariableFetcher.class),
    TIMER(null),
    MODBUS_TCP(ModbusTcpVariableFetcher.class),
    CURRENT_TIME(CurrentTimeFetcher.class);

    Class clazz;

    EVariableFetcherType(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }
}
