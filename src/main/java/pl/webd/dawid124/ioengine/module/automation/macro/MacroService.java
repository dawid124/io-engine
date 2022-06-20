package pl.webd.dawid124.ioengine.module.automation.macro;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.automation.macro.sctructure.MacroStructure;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.Map;

@Service public class MacroService {
    private MacroStructure macroHome;

    public MacroService() {
        this.macroHome = new MacroStructure();
    }

    //    public static void main(String[] args) {
    //        StructureService structureService = new StructureService();
    //        structureService.init();
    //        StateService stateService = new StateService(structureService);
    //        stateService.init();
    //        VariableFetcherJsonAdapter variableFetcher = new VariableFetcherJsonAdapter(
    //                new CurrentStateVariableFetcher(stateService));
    //
    //
    //
    //        RunnerService runnerService = new RunnerService(stateService, null);
    //
    //        TimerService timerService = new TimerService(variableFetcher, runnerService);
    //        timerService.init();
    //
    //        runnerService.setTimerService(timerService);
    //
    //        new MacroService(variableFetcher, runnerService).init();
    //    }

    public void runMacro(Map<String, IVariable> variables, String id) {
        macroHome.getMacros().get(id).run(variables);
    }

    public MacroStructure getMacroHome() {
        return macroHome;
    }

    public void setMacroHome(MacroStructure macroHome) {
        this.macroHome = macroHome;
    }
}
