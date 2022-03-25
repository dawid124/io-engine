package pl.webd.dawid124.ioengine.home.state.variable;

public class StringVariable implements IVariable {

    private String value;

    public StringVariable(String value) {
        this.value = value;
    }

    @Override public EVariableType getType() {
        return EVariableType.STRING;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
