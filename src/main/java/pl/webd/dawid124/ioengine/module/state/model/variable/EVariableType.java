package pl.webd.dawid124.ioengine.module.state.model.variable;

public enum EVariableType {
    STRING(StringVariable.class),
    BOOLEAN(BooleanVariable.class),
    NUMBER(IntegerVariable.class),
    DOUBLE(DoubleVariable.class),
    LIST(ListVariable.class),
    TIME(TimeVariable.class);

    final Class<? extends IVariable> clazz;
    EVariableType(Class<? extends IVariable> clazz) {
        this.clazz = clazz;
    }
    public Class<? extends IVariable> getClazz() {
        return clazz;
    }
}
