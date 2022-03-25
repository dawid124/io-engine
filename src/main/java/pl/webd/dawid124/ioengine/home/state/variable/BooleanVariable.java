package pl.webd.dawid124.ioengine.home.state.variable;

public class BooleanVariable implements IVariable {

    private Boolean value;

    @Override public EVariableType getType() {
        return EVariableType.BOOLEAN;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
