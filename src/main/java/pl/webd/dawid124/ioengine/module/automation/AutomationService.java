package pl.webd.dawid124.ioengine.module.automation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.automation.macro.MacroService;
import pl.webd.dawid124.ioengine.module.automation.macro.block.IBlock;
import pl.webd.dawid124.ioengine.module.automation.macro.block.runner.RunnerService;
import pl.webd.dawid124.ioengine.module.automation.macro.fetcher.IVariableFetcher;
import pl.webd.dawid124.ioengine.module.automation.macro.json.IBlockJsonAdapter;
import pl.webd.dawid124.ioengine.module.automation.macro.json.IVariableJsonAdapter;
import pl.webd.dawid124.ioengine.module.automation.macro.json.VariableFetcherJsonAdapter;
import pl.webd.dawid124.ioengine.module.automation.macro.sctructure.MacroStructure;
import pl.webd.dawid124.ioengine.module.automation.timer.TimerService;
import pl.webd.dawid124.ioengine.module.automation.timer.structure.TimerStructure;
import pl.webd.dawid124.ioengine.module.automation.trigger.TriggerService;
import pl.webd.dawid124.ioengine.module.automation.trigger.TriggerStructure;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.utils.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class AutomationService {

    private final Gson gson;
    private final VariableFetcherJsonAdapter variableFetcherJsonAdapter;
    private final RunnerService runnerService;

    private final TimerService timerService;

    private final MacroService macroService;

    private final TriggerService triggerService;


    public AutomationService(VariableFetcherJsonAdapter variableFetcherJsonAdapter, RunnerService runnerService, TimerService timerService,
            MacroService macroService, TriggerService triggerService) {
        this.variableFetcherJsonAdapter = variableFetcherJsonAdapter;
        this.runnerService = runnerService;
        this.timerService = timerService;
        this.macroService = macroService;
        this.triggerService = triggerService;

        this.gson =  new GsonBuilder()
                .registerTypeAdapter(IVariable.class, new IVariableJsonAdapter())
                .registerTypeAdapter(IBlock.class, new IBlockJsonAdapter(runnerService))
                .registerTypeAdapter(IVariableFetcher.class, variableFetcherJsonAdapter)
                .create();
    }

    @PostConstruct
    public void init() throws IOException {
        macroService.setMacroHome(fetchMacroStructure());
        timerService.setTimerStructure(fetchTimerStructure());
        triggerService.setTriggerStructure(fetchTriggerStructure());
    }

    public TriggerStructure fetchTriggerStructure() throws IOException {
        String structure = ResourceUtils.getResourceFileAsString("classpath:trigger.json");
        return gson.fromJson(structure, TriggerStructure.class);
    }

    public MacroStructure fetchMacroStructure() throws IOException {
        String structure = ResourceUtils.getResourceFileAsString("classpath:macro.json");
        return gson.fromJson(structure, MacroStructure.class);
    }

    public TimerStructure fetchTimerStructure() throws IOException {
        String structure = ResourceUtils.getResourceFileAsString("classpath:timer.json");
        return gson.fromJson(structure, TimerStructure.class);
    }
}
