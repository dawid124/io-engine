package pl.webd.dawid124.ioengine.module.automatization.block.runner;

import pl.webd.dawid124.ioengine.module.automatization.block.AbstractBlock;

import java.util.Map;

public class RunnerBlock extends AbstractBlock {

    private ERunnerBlockType runnerType;
    private Map<String, String> args;

    private RunnerService runnerService;

    public RunnerBlock(ERunnerBlockType runnerType, Map<String, String> args, RunnerService runnerService) {
        this.runnerType = runnerType;
        this.args = args;
        this.runnerService = runnerService;
    }

    @Override
    public void run(Map <String, Object> variables, String zoneId) {
        runnerService.run(variables, zoneId, this);
    }

    public ERunnerBlockType getRunnerType() {
        return runnerType;
    }

    public void setRunnerType(ERunnerBlockType runnerType) {
        this.runnerType = runnerType;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public void setArgs(Map<String, String> args) {
        this.args = args;
    }
}
