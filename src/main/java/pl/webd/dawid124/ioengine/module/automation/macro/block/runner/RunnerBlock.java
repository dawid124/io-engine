package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.automation.macro.block.AbstractBlock;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.Map;

public abstract class RunnerBlock extends AbstractBlock {

    public abstract ERunnerBlockType getRunnerType();

    public abstract void run(AutomationContext context, Map<String, IVariable> variables, String zoneId);

}
