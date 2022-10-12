package pl.webd.dawid124.ioengine.module.automation.macro.block.condition;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.ArrayList;
import java.util.Map;

public class NotCondition implements ICondition {

    private final ICondition condition;

    public NotCondition(ICondition condition) {
        this.condition = condition;
    }

    @Override
    public boolean test(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        return !condition.test(context, variables, zoneId);
    }

    @Override
    public EConditionType getType() {
        return EConditionType.NOT;
    }
}
