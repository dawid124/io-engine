package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import pl.webd.dawid124.ioengine.module.automation.macro.block.AbstractBlock;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.Map;

public class RunnerBlock extends AbstractBlock {

    private ERunnerBlockType runnerType;
    private Map<String, Object> args;

    private RunnerService runnerService;

    public RunnerBlock(ERunnerBlockType runnerType, Map<String, Object> args, RunnerService runnerService) {
        this.runnerType = runnerType;
        this.args = args;
        this.runnerService = runnerService;
    }

    @Override
    public void run(Map <String, IVariable> variables, String zoneId) {
        runnerService.run(variables, zoneId, this);
    }

    public ERunnerBlockType getRunnerType() {
        return runnerType;
    }

    public void setRunnerType(ERunnerBlockType runnerType) {
        this.runnerType = runnerType;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }

    public void setRunnerService(RunnerService runnerService) {
        this.runnerService = runnerService;
    }
}
