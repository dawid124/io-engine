package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import pl.webd.dawid124.ioengine.module.automation.macro.RunnerService;
import pl.webd.dawid124.ioengine.module.automation.macro.block.AbstractBlock;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.Map;

public abstract class RunnerBlock extends AbstractBlock {

    private RunnerService runnerService;

    public abstract ERunnerBlockType getRunnerType();

    public RunnerBlock(RunnerService runnerService) {
        this.runnerService = runnerService;
    }

    @Override
    public void run(Map <String, IVariable> variables, String zoneId) {
        runnerService.run(this, variables, zoneId);
    }

    public void setRunnerService(RunnerService runnerService) {
        this.runnerService = runnerService;
    }
}
