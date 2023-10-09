package pl.webd.dawid124.ioengine.module.automation.trigger;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.SystemArg;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.MotionSensorState;
import pl.webd.dawid124.ioengine.module.state.model.variable.BooleanVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class TriggerService implements MessageHandler {

    private static final Logger LOG = LogManager.getLogger( TriggerService.class );
    private Gson gson = new Gson();

    private TriggerStructure triggerStructure;

    private final AutomationContext context;

    public TriggerService(AutomationContext context) {
        this.context = context;
    }

    public void handleZigbeeMessage(String id, Map<String, IVariable> variables) throws MessagingException {

        Trigger trigger = triggerStructure.getTriggers().get(id);
        if (trigger != null) {
            trigger.run(context, variables);
        }
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        SensorTriggerMsg triggerMsg = gson.fromJson((String) message.getPayload(), SensorTriggerMsg.class);

        DeviceState deviceState = context.getStateService().getSensors().get(triggerMsg.getId());

        if (((MotionSensorState) deviceState).isLock()) {
            return;
        }

        updateSensorState(deviceState, triggerMsg);

        Trigger trigger = triggerStructure.getTriggers().get(triggerMsg.getId());
        if (trigger != null) {
            HashMap<String, IVariable> variables = new HashMap<>();
            variables.put(SystemArg.PIR_VALUE, new BooleanVariable(triggerMsg.isState()));

            trigger.run(context, variables);
        }
    }

    private void updateSensorState(DeviceState deviceState, SensorTriggerMsg triggerMsg) {
        if (deviceState == null || !(deviceState instanceof MotionSensorState)) return;

        if (triggerMsg.isState()) {
            ((MotionSensorState) deviceState).setLastActiveDate(LocalDateTime.now());
        } else {
            ((MotionSensorState) deviceState).setLastDeActiveDate(LocalDateTime.now());
        }
    }

    public TriggerStructure getTriggerStructure() {
        return triggerStructure;
    }

    public void setTriggerStructure(TriggerStructure triggerStructure) {
        this.triggerStructure = triggerStructure;
    }
}
