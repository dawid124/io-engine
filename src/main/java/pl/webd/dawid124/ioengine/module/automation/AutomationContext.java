package pl.webd.dawid124.ioengine.module.automation;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.webd.dawid124.ioengine.modbus.ModbusTcpService;
import pl.webd.dawid124.ioengine.module.action.service.ActionService;
import pl.webd.dawid124.ioengine.module.automation.macro.MacroService;
import pl.webd.dawid124.ioengine.module.automation.timer.TimerService;
import pl.webd.dawid124.ioengine.module.automation.trigger.TriggerService;
import pl.webd.dawid124.ioengine.module.logs.EventLogService;
import pl.webd.dawid124.ioengine.module.state.service.StateService;
import pl.webd.dawid124.ioengine.module.structure.service.StructureService;

@Component
public class AutomationContext {

    private final ApplicationContext appContext;

    public AutomationContext(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public TimerService getTimerService() {
        return (TimerService) appContext.getBean("timerService");
    }

    public MacroService getMacroService() {
        return (MacroService) appContext.getBean("macroService");
    }

    public StateService getStateService() {
        return (StateService) appContext.getBean("stateService");
    }

    public StructureService getStructureService() {
        return (StructureService) appContext.getBean("structureService");
    }

    public ActionService getActionService() {
        return (ActionService) appContext.getBean("actionService");
    }

    public TriggerService getTriggerService() {
        return (TriggerService) appContext.getBean("triggerService");
    }
    public EventLogService getEventLogService() {
        return (EventLogService) appContext.getBean("eventLogService");
    }
    public ModbusTcpService getModbusTcpService() {
        return (ModbusTcpService) appContext.getBean("modbusTcpService");
    }
}
