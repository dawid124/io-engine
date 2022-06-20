package pl.webd.dawid124.ioengine.module.state.model.variable;

import pl.webd.dawid124.ioengine.module.automation.macro.exception.WrongVariableTypeException;

public class BooleanVariable implements IVariable {

    private Boolean value;

    public BooleanVariable() {}

    public BooleanVariable(boolean value) {
        this.value = value;
    }

    @Override public EVariableType getType() {
        return EVariableType.BOOLEAN;
    }

    @Override
    public boolean equals(IVariable test) {
        if (!(test instanceof BooleanVariable)) throw new WrongVariableTypeException();

        BooleanVariable booleanVariable = (BooleanVariable) test;
        return value.equals(booleanVariable.value);
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
