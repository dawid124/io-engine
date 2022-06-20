package pl.webd.dawid124.ioengine.module.state.model.variable;

import pl.webd.dawid124.ioengine.module.automatization.exception.WrongVariableTypeException;

public class NumberVariable implements IVariable {

    private Integer value;

    @Override public EVariableType getType() {
        return EVariableType.NUMBER;
    }

    @Override
    public boolean equals(IVariable test) {
        NumberVariable numberVariable = getNumberVariable(test);
        return value.equals(numberVariable.value);
    }

    private NumberVariable getNumberVariable(IVariable test) {
        if (!(test instanceof NumberVariable)) throw new WrongVariableTypeException();

        return (NumberVariable) test;
    }

    @Override
    public boolean lt(IVariable test) {
        NumberVariable numberVariable = getNumberVariable(test);
        return value < numberVariable.value;
    }

    @Override
    public boolean gt(IVariable test) {
        NumberVariable numberVariable = getNumberVariable(test);
        return value > numberVariable.value;
    }

    @Override
    public boolean le(IVariable test) {
        NumberVariable numberVariable = getNumberVariable(test);
        return value <= numberVariable.value;
    }

    @Override
    public boolean ge(IVariable test) {
        NumberVariable numberVariable = getNumberVariable(test);
        return value >= numberVariable.value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
