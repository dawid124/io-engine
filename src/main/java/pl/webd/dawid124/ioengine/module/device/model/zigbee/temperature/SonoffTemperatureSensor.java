package pl.webd.dawid124.ioengine.module.device.model.zigbee.temperature;

import com.google.gson.Gson;
import org.springframework.messaging.Message;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;
import pl.webd.dawid124.ioengine.module.device.model.zigbee.ZigbeeAction;
import pl.webd.dawid124.ioengine.module.device.model.zigbee.ZigbeeDevice;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.EDeviceStateType;
import pl.webd.dawid124.ioengine.module.state.model.device.MqttTemperatureSensorState;
import pl.webd.dawid124.ioengine.module.zigbee.devices.SonoffSNZB02Msg;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfig;

public class SonoffTemperatureSensor extends ZigbeeDevice {

    private final Gson gson = new Gson();

    public SonoffTemperatureSensor(String id, String name, IDriverConfiguration driverConfiguration) {
        super(id, name, driverConfiguration);
    }

    @Override
    public void processIncomingMsg(AutomationContext context, Message<?> message) {
        SonoffSNZB02Msg msg = gson.fromJson((String) message.getPayload(), SonoffSNZB02Msg.class);

        MqttTemperatureSensorState deviceState = (MqttTemperatureSensorState)
                context.getStateService().getSensors().get(getId());

        deviceState.setTemperature(msg.getTemperature());
        deviceState.setHumidity(msg.getHumidity());
        deviceState.setBattery(msg.getBattery());
    }

    @Override
    public ZigbeeAction processAction(String action, Object params) {
        return null;
    }

    @Override
    public EDeviceType getIoType() {
        return EDeviceType.MQTT_TEMPERATURE_SENSOR;
    }

    @Override
    public DeviceState getInitialState() {
        return new MqttTemperatureSensorState(getId(), getName(), EDeviceStateType.MQTT_TEMPERATURE_SENSOR);
    }

    @Override
    public IoConfig toIoConfig() {
        return null;
    }
}
