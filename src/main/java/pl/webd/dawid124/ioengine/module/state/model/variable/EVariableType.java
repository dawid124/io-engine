package pl.webd.dawid124.ioengine.module.state.model.variable;

public enum EVariableType {
    STRING(StringVariable.class),
    BOOLEAN(BooleanVariable.class),
    NUMBER(IntegerVariable.class),
    DOUBLE(DoubleVariable.class),
    LIST(ListVariable.class),
    TIME(TimeVariable.class);

    Class clazz;
    EVariableType(Class clazz) {
        this.clazz = clazz;
    }
    public Class getClazz() {
        return clazz;
    }
}
