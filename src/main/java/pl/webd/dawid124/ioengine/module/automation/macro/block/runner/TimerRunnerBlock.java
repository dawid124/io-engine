package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;

import pl.webd.dawid124.ioengine.module.automation.macro.block.IBlock;
import pl.webd.dawid124.ioengine.module.automation.timer.structure.ETimerAction;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.List;
import java.util.Map;

public class TimerRunnerBlock extends RunnerBlock {

    private final String timerId;
    private final ETimerAction action;
    private final Integer time;
    private final List<IBlock> blocs;

    public TimerRunnerBlock(String timerId, ETimerAction action, Integer time, List<IBlock> blocs) {
        this.timerId = timerId;
        this.action = action;
        this.time = time;
        this.blocs = blocs;
    }

    @Override public ERunnerBlockType getRunnerType() {
        return ERunnerBlockType.TIMER;
    }

    @Override
    public void run(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        context.getTimerService().runTimer(variables, zoneId, this);
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

    public List<IBlock> getBlocs() {
        return blocs;
    }
}
