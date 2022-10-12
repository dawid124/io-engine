package pl.webd.dawid124.ioengine.module.automation.macro.block.condition;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.automation.macro.fetcher.IVariableFetcher;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.ListVariable;

import java.util.Map;

public class CompareCondition implements ICondition {

    private final ECompareCondition compareType;

    private final IVariableFetcher fetcher;
    private final IVariable testValue;


    public CompareCondition(ECompareCondition compareType, IVariableFetcher fetcher, IVariable testValue) {
        this.compareType = compareType;
        this.fetcher = fetcher;
        this.testValue = testValue;
    }

    @Override
    public boolean test(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        IVariable variable = fetcher.fetch(context, variables, zoneId);

        switch (compareType) {
            case EQUAL:
                return variable.equals(testValue);
            case NOT_EQUAL:
                return variable.notEquals(testValue);
            case CONTAIN:
                return variable.contains(testValue);
            case LESS_THEN:
                return variable.lt(testValue);
            case LESS_EQUALS:
                return variable.le(testValue);
            case GREATER_THEN:
                return variable.gt(testValue);
            case GREATER_EQUALS:
                return variable.ge(testValue);
            case IN:
                ListVariable list = (ListVariable) testValue;
                return variable.in(list.getValue());
        }

        return false;
    }

    @Override
    public EConditionType getType() {
        return EConditionType.COMPARE;
    }

    public ECompareCondition getCompareType() {
        return compareType;
    }

    public IVariableFetcher getFetcher() {
        return fetcher;
    }


    public IVariable getTestValue() {
        return testValue;
    }
}
