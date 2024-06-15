package pl.webd.dawid124.ioengine.module.state.model.variable;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.automation.macro.block.condition.ICondition;

import java.util.HashMap;
import java.util.Map;

public class ConditionVariable {

    private final ICondition condition;
    private final IVariable variable;
    private final Map<String, IVariable> variables;

    public ConditionVariable(ICondition condition, IVariable variable) {
        this.condition = condition;
        this.variable = variable;
        this.variables = new HashMap<>();
    }

    public IVariable execute(AutomationContext context, String secondVariable, Map<String, IVariable> vars, String zoneId) {
        if (condition.test(context, vars, zoneId)) {
            if (secondVariable == null) {
                return variable;
            } else {
                return variables.get(secondVariable);
            }
        } else {
            return null;
        }
    }

    public ICondition getCondition() {
        return condition;
    }

    public IVariable getVariable() {
        return variable;
    }

    public Map<String, IVariable> getVariables() {
        return variables;
    }
}
