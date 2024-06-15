package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.variable.ConditionVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.List;
import java.util.Map;

public class ConditionVariableRunner {

    private final List<ConditionVariable> conditions;

    public ConditionVariableRunner(List<ConditionVariable> conditions) {
        this.conditions = conditions;
    }

    public IVariable execute(AutomationContext context, String secondVariable, Map<String, IVariable> variables, String zoneId) {
        for (ConditionVariable variable: conditions) {
            IVariable value = variable.execute(context, secondVariable, variables, zoneId);
            if (value != null) return value;
        }

        return null;
    }
}
