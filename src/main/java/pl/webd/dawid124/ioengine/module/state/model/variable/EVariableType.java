package pl.webd.dawid124.ioengine.module.state.model.variable;

public enum EVariableType {
    STRING(StringVariable.class),
    BOOLEAN(BooleanVariable.class),
    NUMBER(NumberVariable.class),
    LIST(ListVariable.class);

    Class clazz;
    EVariableType(Class clazz) {
        this.clazz = clazz;
    }
    public Class getClazz() {
        return clazz;
    }
}
