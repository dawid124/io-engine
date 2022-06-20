package pl.webd.dawid124.ioengine.module.automatization.block.condition;

import pl.webd.dawid124.ioengine.module.automatization.fetcher.IVariableFetcher;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

public class Condition {

    private ELogicalCondition logicalCondition;

    private IVariableFetcher fetcher;
    private IVariable testValue;

    public boolean test(String zoneId) {
        IVariable variable = fetcher.fetch(zoneId);

        switch (logicalCondition) {
            case EQUAL:
                return variable.equals(testValue);
            case NOT_EQUAL:
                return !variable.notEquals(testValue);
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
        }

        return false;
    }

    public IVariableFetcher getFetcher() {
        return fetcher;
    }

    public void setFetcher(IVariableFetcher fetcher) {
        this.fetcher = fetcher;
    }

    public ELogicalCondition getLogicalCondition() {
        return logicalCondition;
    }

    public void setLogicalCondition(ELogicalCondition logicalCondition) {
        this.logicalCondition = logicalCondition;
    }

    public IVariable getTestValue() {
        return testValue;
    }

    public void setTestValue(IVariable testValue) {
        this.testValue = testValue;
    }
}
