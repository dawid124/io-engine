package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.action.model.server.LedChangeData;
import pl.webd.dawid124.ioengine.module.automation.macro.RunnerService;

import java.util.List;

public class LightActionRunnerBlock extends RunnerBlock {

    private final List<UiAction> actions;

    private final LedChangeData ledChangeData;

    public LightActionRunnerBlock(RunnerService runnerService, List<UiAction> actions, LedChangeData ledChangeData) {
        super(runnerService);
        this.actions = actions;
        this.ledChangeData = ledChangeData;
    }

    @Override public ERunnerBlockType getRunnerType() {
        return ERunnerBlockType.LIGHT_ACTION;
    }

    public List<UiAction> getActions() {
        return actions;
    }

    public LedChangeData getLedChangeData() {
        return ledChangeData;
    }
}
