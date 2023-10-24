package pl.webd.dawid124.ioengine.module.state.model.variable;

import pl.webd.dawid124.ioengine.module.automation.macro.exception.WrongVariableTypeException;

public class IntegerVariable implements IVariable {

    private Integer value;

    public IntegerVariable(Integer value) {
        this.value = value;
    }

    @Override public EVariableType getType() {
        return EVariableType.NUMBER;
    }

    @Override
    public boolean equals(IVariable test) {
        IntegerVariable integerVariable = getNumberVariable(test);
        return value.equals(integerVariable.value);
    }

    private IntegerVariable getNumberVariable(IVariable test) {
        if (!(test instanceof IntegerVariable)) throw new WrongVariableTypeException();

        return (IntegerVariable) test;
    }

    @Override
    public boolean lt(IVariable test) {
        IntegerVariable integerVariable = getNumberVariable(test);
        return value < integerVariable.value;
    }

    @Override
    public boolean gt(IVariable test) {
        IntegerVariable integerVariable = getNumberVariable(test);
        return value > integerVariable.value;
    }

    @Override
    public boolean le(IVariable test) {
        IntegerVariable integerVariable = getNumberVariable(test);
        return value <= integerVariable.value;
    }

    @Override
    public boolean ge(IVariable test) {
        IntegerVariable integerVariable = getNumberVariable(test);
        return value >= integerVariable.value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
