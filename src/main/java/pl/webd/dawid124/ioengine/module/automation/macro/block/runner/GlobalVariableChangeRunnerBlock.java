package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.StateVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.Map;

public class GlobalVariableChangeRunnerBlock extends RunnerBlock {

    private final String key;
    private final IVariable value;

    public GlobalVariableChangeRunnerBlock(String key, IVariable value) {
        this.key = key;
        this.value = value;
    }

    @Override public ERunnerBlockType getRunnerType() {
        return ERunnerBlockType.GLOBAL_VARIABLE_CHANGE;
    }

    @Override
    public void run(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        context.getStateService().updateVariable(key, new StateVariable(key, value));
    }

    public String getKey() {
        return key;
    }

    public IVariable getValue() {
        return value;
    }
}
