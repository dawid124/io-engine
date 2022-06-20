package pl.webd.dawid124.ioengine.module.device.model.input;

import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.module.device.model.output.Device;
import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.EDeviceStateType;
import pl.webd.dawid124.ioengine.module.state.model.device.MotionSensorState;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfig;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfigMotionSensor;

public class MotionSensor extends Device {

    private final int pin;
    private final boolean reverse;

    public MotionSensor(String id, String name, IDriverConfiguration driverConfiguration, int pin) {
        super(id, name, driverConfiguration);
        this.pin = pin;
        this.reverse = false;
    }


    public MotionSensor(String id, String name, IDriverConfiguration driverConfiguration, int pin, boolean reverse) {
        super(id, name, driverConfiguration);
        this.pin = pin;
        this.reverse = reverse;
    }

    @Override
    public EDeviceType getIoType() {
        return EDeviceType.MOTION_SENSOR;
    }

    @Override
    public DeviceState getInitialState() {
        return new MotionSensorState(id, name, EDeviceStateType.MOTION_SENSOR, false);
    }

    @Override
    public IoConfig toIoConfig() {
        return new IoConfigMotionSensor(id, getIoType(), pin, reverse);
    }

    public int getPin() {
        return pin;
    }
}
