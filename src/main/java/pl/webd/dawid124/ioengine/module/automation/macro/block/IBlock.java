package pl.webd.dawid124.ioengine.module.automation.macro.block;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.Map;

public interface IBlock {

    String getName();

    Integer getOrder();

    EBlockType getBlockType();

    void run(AutomationContext context, Map<String, IVariable> variables, String zoneId);
}
