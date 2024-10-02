package pl.webd.dawid124.ioengine.module.automation.macro.block.condition;

public enum EConditionType {
    LOGIC(LogicCondition.class),
    NOT(NotCondition.class),
    COMPARE(CompareCondition.class),
    TIME_BETWEEN(TimeBetweenCondition.class);

    final Class<? extends ICondition> clazz;

    EConditionType(Class<? extends ICondition> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends ICondition> getClazz() {
        return clazz;
    }
}
