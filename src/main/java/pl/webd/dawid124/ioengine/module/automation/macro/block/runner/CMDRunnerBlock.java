package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import pl.webd.dawid124.ioengine.module.automation.macro.RunnerService;

public class CMDRunnerBlock extends RunnerBlock {

    private String cmd;

    public CMDRunnerBlock(RunnerService runnerService, String cmd) {
        super(runnerService);
        this.cmd = cmd;
    }

    @Override
    public ERunnerBlockType getRunnerType() {
        return ERunnerBlockType.CMD;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
