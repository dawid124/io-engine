package pl.webd.dawid124.ioengine.module.automation.macro.block.condition;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.ArrayList;
import java.util.Map;

public class LogicCondition implements ICondition {

    private final ELogicalType logicalType;

    private final ArrayList<ICondition> conditions;

    public LogicCondition(ELogicalType logicalType, ArrayList<ICondition> conditions) {
        this.logicalType = logicalType;
        this.conditions = conditions;
    }

    @Override
    public boolean test(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        switch (logicalType) {
            case AND:
                return testAnd(context, variables, zoneId);
            case OR:
                return testOr(context, variables, zoneId);
        }

        return false;
    }

    @Override
    public EConditionType getType() {
        return EConditionType.LOGIC;
    }

    private boolean testOr(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        for (ICondition condition : conditions) {
            if (condition.test(context, variables, zoneId)) return true;
        }

        return false;
    }

    private boolean testAnd(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        for (ICondition condition : conditions) {
            if (!condition.test(context, variables, zoneId)) return false;
        }

        return true;
    }
}
