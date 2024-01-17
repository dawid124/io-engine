package pl.webd.dawid124.ioengine.module.device.model.zigbee.switchs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;
import pl.webd.dawid124.ioengine.module.device.model.zigbee.ZigbeeAction;
import pl.webd.dawid124.ioengine.module.device.model.zigbee.ZigbeeDevice;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.EDeviceStateType;
import pl.webd.dawid124.ioengine.module.state.model.device.ZigbeeDeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.ZigbeeSwitchDeviceState;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfig;

public class Sonoff1GangSwitch extends ZigbeeDevice {

    private static final String SET_SUFFIX = "/set";
    private static final String JSON_OBJECT = "{\"state\": \"%s\"}";

    public Sonoff1GangSwitch(String id, String name, IDriverConfiguration driverConfiguration) {
        super(id, name, driverConfiguration);
    }

    @Override
    public EDeviceType getIoType() {
        return EDeviceType.SWITCH;
    }

    @Override
    public DeviceState getInitialState() {
        return new ZigbeeSwitchDeviceState(id, name, EDeviceStateType.ZIGBEE_SWITCH, false);
    }

    @Override
    public IoConfig toIoConfig() {
        return null;
    }

    @Override
    public void processIncomingMsg(AutomationContext context, ZigbeeDeviceState deviceState, JsonElement message) {
        JsonObject obj = message.getAsJsonObject();
        if ("ON".equals(obj.get("state").getAsString())) {
            ((ZigbeeSwitchDeviceState) deviceState).setOn(true);
        } else {
            ((ZigbeeSwitchDeviceState) deviceState).setOn(false);
        }
    }

    @Override
    public ZigbeeAction processAction(String action, Object params) {
        switch (action) {
            case "ON":
                return new ZigbeeAction(id + SET_SUFFIX, buildOnAction());
            case "OFF":
                return new ZigbeeAction(id + SET_SUFFIX, buildOffAction());
            default:
                return null;
        }
    }

    public String buildOnAction() {
        return String.format(JSON_OBJECT, "ON");
    }

    public String buildOffAction() {
        return String.format(JSON_OBJECT, "OFF");
    }
}
