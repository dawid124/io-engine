package pl.webd.dawid124.ioengine.module.automation.macro.block.condition;

public enum EConditionType {
    LOGIC(LogicCondition.class),
    NOT(NotCondition.class),
    COMPARE(CompareCondition.class);

    Class clazz;

    EConditionType(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }
}
