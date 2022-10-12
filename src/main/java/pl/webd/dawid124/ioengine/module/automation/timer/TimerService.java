package pl.webd.dawid124.ioengine.module.automation.timer;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.automation.macro.block.runner.TimerRunnerBlock;
import pl.webd.dawid124.ioengine.module.automation.timer.structure.Timer;
import pl.webd.dawid124.ioengine.module.automation.timer.structure.TimerStructure;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class TimerService {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(12);

    private final AutomationContext context;

    private TimerStructure timerStructure;

    public TimerService(AutomationContext context) {
        this.context = context;
        this.timerStructure = new TimerStructure();
    }


    public void runTimer(Map<String, IVariable> variables, TimerRunnerBlock timerRunner) {
        Timer timer = timerStructure.getTimers().get(timerRunner.getTimerId());
        switch (timerRunner.getAction()) {
            case RUN:
            case UPDATE_TIME:
                timer.run(context, scheduler, variables, timerRunner.getTime());
                break;
            case CLEAR:
                timer.cancel();
                break;
        }
    }

    @PreDestroy
    public void destructor() {
        scheduler.shutdown();
    }

    public TimerStructure getTimerStructure() {
        return timerStructure;
    }

    public void setTimerStructure(TimerStructure timerStructure) {
        this.timerStructure = timerStructure;
    }
}
