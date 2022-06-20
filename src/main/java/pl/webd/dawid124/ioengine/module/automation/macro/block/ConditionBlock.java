package pl.webd.dawid124.ioengine.module.automation.macro.block;

import pl.webd.dawid124.ioengine.module.automation.macro.block.condition.Condition;
import pl.webd.dawid124.ioengine.module.automation.macro.block.condition.EConditionBlockType;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConditionBlock extends AbstractBlock {

    private static final int SIMPLE_CONDITION_LIST_INDEX = 0;
    private EConditionBlockType conditionType;

    private List<Condition> conditions;

    private IBlock trueBlock;
    private IBlock falseBlock;

    public ConditionBlock() {
        conditions = new ArrayList<>();
    }

    @Override
    public void run(Map<String, IVariable> variables, String zoneId) {
        if (test(variables, zoneId)) {
            if (trueBlock != null) trueBlock.run(variables, zoneId);
        } else {
            if (falseBlock != null) falseBlock.run(variables, zoneId);
        }
    }


    private boolean test(Map<String, IVariable> variables, String zoneId) {
        switch (conditionType) {
            case SIMPLE:
                return testSimple(variables, zoneId);
            case AND:
                return testAnd(variables, zoneId);
            case OR:
                return testOr(variables, zoneId);
        }

        return false;
    }

    private boolean testOr(Map<String, IVariable> variables, String zoneId) {
        for (Condition condition : conditions) {
            if (condition.test(variables, zoneId)) return true;
        }

        return false;
    }

    private boolean testAnd(Map<String, IVariable> variables, String zoneId) {
        for (Condition condition : conditions) {
            if (!condition.test(variables, zoneId)) return false;
        }

        return true;
    }

    private boolean testSimple(Map<String, IVariable> variables, String zoneId) {
        return conditions.get(SIMPLE_CONDITION_LIST_INDEX).test(variables, zoneId);
    }

    public IBlock getTrueBlock() {
        return trueBlock;
    }

    public void setTrueBlock(IBlock trueBlock) {
        this.trueBlock = trueBlock;
    }

    public IBlock getFalseBlock() {
        return falseBlock;
    }

    public void setFalseBlock(IBlock falseBlock) {
        this.falseBlock = falseBlock;
    }

    public EConditionBlockType getConditionType() {
        return conditionType;
    }

    public void setConditionType(EConditionBlockType conditionType) {
        this.conditionType = conditionType;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
}
