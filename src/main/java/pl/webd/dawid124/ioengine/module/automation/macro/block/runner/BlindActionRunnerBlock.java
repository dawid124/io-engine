package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import pl.webd.dawid124.ioengine.module.action.model.rest.IUiAction;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.List;
import java.util.Map;

public class BlindActionRunnerBlock extends RunnerBlock {

    private final List<IUiAction> actions;


    public BlindActionRunnerBlock(List<IUiAction> actions) {
        this.actions = actions;
    }

    @Override public ERunnerBlockType getRunnerType() {
        return ERunnerBlockType.BLIND_ACTION;
    }

    @Override
    public void run(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        context.getActionService().processBlinds(actions);
    }

    public List<IUiAction> getActions() {
        return actions;
    }
}
