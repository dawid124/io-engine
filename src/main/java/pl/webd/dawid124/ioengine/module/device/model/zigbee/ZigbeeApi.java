package pl.webd.dawid124.ioengine.module.device.model.zigbee;

import com.google.gson.JsonElement;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.device.ZigbeeDeviceState;

public interface ZigbeeApi {

    default void processIncomingMsg(AutomationContext context, ZigbeeDeviceState deviceState, JsonElement message) {};
    default ZigbeeAction processAction(String action, Object params) {
        return null;
    }
}
