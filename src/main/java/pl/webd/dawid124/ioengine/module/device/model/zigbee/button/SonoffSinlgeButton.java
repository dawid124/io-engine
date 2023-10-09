package pl.webd.dawid124.ioengine.module.device.model.zigbee.button;

import com.google.gson.Gson;
import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;
import pl.webd.dawid124.ioengine.module.device.model.zigbee.ZigbeeAction;
import pl.webd.dawid124.ioengine.module.device.model.zigbee.ZigbeeDevice;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.EDeviceStateType;
import pl.webd.dawid124.ioengine.module.state.model.device.ZigbeeStateLessDeviceState;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfig;

public class SonoffSinlgeButton extends ZigbeeDevice {

    private final Gson gson = new Gson();

    public SonoffSinlgeButton(String id, String name, IDriverConfiguration driverConfiguration) {
        super(id, name, driverConfiguration);
    }

    @Override
    public ZigbeeAction processAction(String action, Object params) {
        return null;
    }

    @Override
    public EDeviceType getIoType() {
        return EDeviceType.MQTT_BUTTON;
    }

    @Override
    public DeviceState getInitialState() {
        return new ZigbeeStateLessDeviceState(id, name, EDeviceStateType.ZIGBEE_STATE_LESS);
    }

    @Override
    public IoConfig toIoConfig() {
        return null;
    }
}
