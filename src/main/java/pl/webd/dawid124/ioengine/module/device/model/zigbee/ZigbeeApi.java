package pl.webd.dawid124.ioengine.module.device.model.zigbee;

import org.springframework.messaging.Message;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;

public interface ZigbeeApi {

    default void processIncomingMsg(AutomationContext context, Message<?> message) {};
    default ZigbeeAction processAction(String action, Object params) {
        return null;
    }
}
