package pl.webd.dawid124.ioengine.module.automation.trigger;

import com.google.gson.Gson;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.rest.EActionType;
import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.module.device.service.DeviceService;
import pl.webd.dawid124.ioengine.module.action.model.rest.Color;
import pl.webd.dawid124.ioengine.module.state.SystemArg;
import pl.webd.dawid124.ioengine.module.state.model.rest.ZoneStateResponse;
import pl.webd.dawid124.ioengine.module.state.model.variable.BooleanVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.service.StateService;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;
import pl.webd.dawid124.ioengine.mqtt.MqttService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class TriggerService implements MessageHandler {


    private Gson gson = new Gson();

    private TriggerStructure triggerStructure;


    public TriggerService() {}

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        SensorTriggerMsg triggerMsg = gson.fromJson((String) message.getPayload(), SensorTriggerMsg.class);

        Trigger trigger = triggerStructure.getTriggers().get(triggerMsg.getId());

        if (trigger != null) {
            HashMap<String, IVariable> variables = new HashMap<>();
            variables.put(SystemArg.PIR_VALUE, new BooleanVariable(triggerMsg.isState()));

            trigger.run(variables);
        }
    }

    public TriggerStructure getTriggerStructure() {
        return triggerStructure;
    }

    public void setTriggerStructure(TriggerStructure triggerStructure) {
        this.triggerStructure = triggerStructure;
    }
}
