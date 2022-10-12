package pl.webd.dawid124.ioengine.module.automation.macro.block;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.automation.macro.block.condition.ICondition;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.Map;

public class ConditionBlock extends AbstractBlock {

    private final ICondition condition;

    private final IBlock trueBlock;
    private final IBlock falseBlock;

    public ConditionBlock(ICondition condition, IBlock trueBlock, IBlock falseBlock) {
        this.condition = condition;
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
    }

    @Override
    public void run(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        if (condition.test(context, variables, zoneId)) {
            if (trueBlock != null) trueBlock.run(context, variables, zoneId);
        } else {
            if (falseBlock != null) falseBlock.run(context, variables, zoneId);
        }
    }

    public IBlock getTrueBlock() {
        return trueBlock;
    }

    public IBlock getFalseBlock() {
        return falseBlock;
    }

    public ICondition getCondition() {
        return condition;
    }
}
