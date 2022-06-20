package pl.webd.dawid124.ioengine.module.automatization.block;

import pl.webd.dawid124.ioengine.module.automatization.block.condition.Condition;
import pl.webd.dawid124.ioengine.module.automatization.block.condition.EConditionBlockType;

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
    public void run(Map<String, Object> variables, String zoneId) {
        if (test(zoneId)) {
            trueBlock.run(variables, zoneId);
        } else {
            falseBlock.run(variables, zoneId);
        }
    }


    private boolean test(String zoneId) {
        switch (conditionType) {
            case SIMPLE:
                return testSimple(zoneId);
            case AND:
                return testAnd(zoneId);
            case OR:
                return testOr(zoneId);
        }

        return false;
    }

    private boolean testOr(String zoneId) {
        for (Condition condition : conditions) {
            if (condition.test(zoneId)) return true;
        }

        return false;
    }

    private boolean testAnd(String zoneId) {
        for (Condition condition : conditions) {
            if (!condition.test(zoneId)) return false;
        }

        return true;
    }

    private boolean testSimple(String zoneId) {
        return conditions.get(SIMPLE_CONDITION_LIST_INDEX).test(zoneId);
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
