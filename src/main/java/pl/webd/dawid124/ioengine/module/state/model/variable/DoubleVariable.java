package pl.webd.dawid124.ioengine.module.state.model.variable;

import pl.webd.dawid124.ioengine.module.automation.macro.exception.WrongVariableTypeException;

public class DoubleVariable implements IVariable {

    private Double value;

    public DoubleVariable(double doubleValue) {
        this.value = doubleValue;
    }

    @Override public EVariableType getType() {
        return EVariableType.NUMBER;
    }

    @Override
    public boolean equals(IVariable test) {
        DoubleVariable integerVariable = getNumberVariable(test);
        return value.equals(integerVariable.value);
    }

    private DoubleVariable getNumberVariable(IVariable test) {
        if (!(test instanceof DoubleVariable)) throw new WrongVariableTypeException();

        return (DoubleVariable) test;
    }

    @Override
    public boolean lt(IVariable test) {
        DoubleVariable integerVariable = getNumberVariable(test);
        return value < integerVariable.value;
    }

    @Override
    public boolean gt(IVariable test) {
        DoubleVariable integerVariable = getNumberVariable(test);
        return value > integerVariable.value;
    }

    @Override
    public boolean le(IVariable test) {
        DoubleVariable integerVariable = getNumberVariable(test);
        return value <= integerVariable.value;
    }

    @Override
    public boolean ge(IVariable test) {
        DoubleVariable integerVariable = getNumberVariable(test);
        return value >= integerVariable.value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
