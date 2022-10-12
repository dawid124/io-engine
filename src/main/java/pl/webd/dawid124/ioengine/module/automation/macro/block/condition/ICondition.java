package pl.webd.dawid124.ioengine.module.automation.macro.block.condition;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.Map;

public interface ICondition {

    boolean test(AutomationContext context, Map<String, IVariable> variables, String zoneId);

    EConditionType getType();

}
