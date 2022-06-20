package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import pl.webd.dawid124.ioengine.module.automation.macro.RunnerService;
import pl.webd.dawid124.ioengine.module.automation.timer.structure.ETimerAction;

public class TimerRunnerBlock extends RunnerBlock {

    private final String timerId;
    private final ETimerAction action;
    private final Integer time;

    public TimerRunnerBlock(RunnerService runnerService, String timerId, ETimerAction action, Integer time) {
        super(runnerService);
        this.timerId = timerId;
        this.action = action;
        this.time = time;
    }

    @Override public ERunnerBlockType getRunnerType() {
        return ERunnerBlockType.TIMER;
    }

    public String getTimerId() {
        return timerId;
    }

    public ETimerAction getAction() {
        return action;
    }

    public Integer getTime() {
        return time;
    }
}
