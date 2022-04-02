package pl.webd.dawid124.ioengine.module.state.model.variable;

public class NumberVariable implements IVariable {

    private Integer value;

    @Override public EVariableType getType() {
        return EVariableType.NUMBER;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
