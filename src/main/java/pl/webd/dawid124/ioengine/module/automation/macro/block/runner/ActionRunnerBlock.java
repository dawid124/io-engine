package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.action.model.server.LedChangeData;
import pl.webd.dawid124.ioengine.module.automation.macro.RunnerService;

import java.util.List;

public class ActionRunnerBlock extends RunnerBlock {

    private final List<UiAction> actions;


    public ActionRunnerBlock(RunnerService runnerService, List<UiAction> actions) {
        super(runnerService);
        this.actions = actions;
    }

    @Override public ERunnerBlockType getRunnerType() {
        return ERunnerBlockType.ACTION;
    }

    public List<UiAction> getActions() {
        return actions;
    }
}
