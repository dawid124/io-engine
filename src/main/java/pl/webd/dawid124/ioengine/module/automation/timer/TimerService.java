package pl.webd.dawid124.ioengine.module.automation.timer;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.automation.macro.block.runner.TimerRunnerBlock;
import pl.webd.dawid124.ioengine.module.automation.timer.structure.ETimerAction;
import pl.webd.dawid124.ioengine.module.automation.timer.structure.Timer;
import pl.webd.dawid124.ioengine.module.automation.timer.structure.TimerStructure;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class TimerService {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(12);
    public static final String ARGS_ID = "id";
    public static final String ARGS_ACTION = "action";
    public static final String ARGS_TIME = "time";
    private TimerStructure timerStructure;

    public TimerService() {
        this.timerStructure = new TimerStructure();
    }

//    public static void main(String[] args) {
//        StructureService structureService = new StructureService();
//        structureService.init();
//        StateService stateService = new StateService(structureService);
//        stateService.init();
//        VariableFetcherJsonAdapter variableFetcher = new VariableFetcherJsonAdapter(
//                new CurrentStateVariableFetcher(stateService));
//
//        RunnerService runnerService = new RunnerService(stateService, null);
//
//        TimerService timerService = new TimerService(variableFetcher, runnerService);
//        timerService.init();
//        runnerService.setTimerService(timerService);
//    }

    public void runTimer(Map<String, IVariable> variables, TimerRunnerBlock timerRunner) {
        Timer timer = timerStructure.getTimers().get(timerRunner.getTimerId());
        switch (timerRunner.getAction()) {
            case RUN:
            case UPDATE_TIME:
                timer.run(scheduler, variables, timerRunner.getTime());
                break;
            case CLEAR:
                timer.cancel();
                break;
        }
    }

    public TimerStructure getTimerStructure() {
        return timerStructure;
    }

    public void setTimerStructure(TimerStructure timerStructure) {
        this.timerStructure = timerStructure;
    }
}
