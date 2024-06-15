package pl.webd.dawid124.ioengine.module.automation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.rest.Color;
import pl.webd.dawid124.ioengine.module.action.model.rest.IUiAction;
import pl.webd.dawid124.ioengine.module.automation.cron.CronService;
import pl.webd.dawid124.ioengine.module.automation.cron.CronStructure;
import pl.webd.dawid124.ioengine.module.automation.macro.MacroService;
import pl.webd.dawid124.ioengine.module.automation.macro.block.IBlock;
import pl.webd.dawid124.ioengine.module.automation.macro.block.condition.ICondition;
import pl.webd.dawid124.ioengine.module.automation.macro.fetcher.IVariableFetcher;
import pl.webd.dawid124.ioengine.module.automation.macro.json.*;
import pl.webd.dawid124.ioengine.module.automation.macro.sctructure.MacroStructure;
import pl.webd.dawid124.ioengine.module.automation.timer.TimerService;
import pl.webd.dawid124.ioengine.module.automation.timer.structure.TimerStructure;
import pl.webd.dawid124.ioengine.module.automation.trigger.TriggerService;
import pl.webd.dawid124.ioengine.module.automation.trigger.TriggerStructure;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.utils.AppGsonBuilder;
import pl.webd.dawid124.ioengine.utils.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class AutomationService {

    private final Gson gson;

    private final TimerService timerService;

    private final MacroService macroService;

    private final TriggerService triggerService;

    private final CronService cronService;


    public AutomationService(TimerService timerService, MacroService macroService, TriggerService triggerService,
                             CronService cronService, AutomationContext automationContext) {
        this.timerService = timerService;
        this.macroService = macroService;
        this.triggerService = triggerService;
        this.cronService = cronService;

        this.gson = AppGsonBuilder.instance(automationContext);
    }

    @PostConstruct
    public void init() throws IOException {
        macroService.setMacroHome(fetchMacroStructure());
        timerService.setTimerStructure(fetchTimerStructure());
        triggerService.setTriggerStructure(fetchTriggerStructure());
        cronService.setCronStructure(fetchCronStructure());
    }

    public TriggerStructure fetchTriggerStructure() throws IOException {
        String structure = ResourceUtils.getResourceYamlAsJson("classpath:trigger.yaml");
        return gson.fromJson(structure, TriggerStructure.class);
    }

    public MacroStructure fetchMacroStructure() throws IOException {
        String structure = ResourceUtils.getResourceYamlAsJson("classpath:macro.yaml");
        return gson.fromJson(structure, MacroStructure.class);
    }

    public TimerStructure fetchTimerStructure() throws IOException {
        String structure = ResourceUtils.getResourceYamlAsJson("classpath:timer.yaml");
        return gson.fromJson(structure, TimerStructure.class);
    }

    public CronStructure fetchCronStructure() throws IOException {
        String structure = ResourceUtils.getResourceYamlAsJson("classpath:cron.yaml");
        return gson.fromJson(structure, CronStructure.class);
    }
}
