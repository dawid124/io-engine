package pl.webd.dawid124.ioengine.module.state.model.variable;

import pl.webd.dawid124.ioengine.module.automation.macro.exception.WrongVariableTypeException;

import java.time.LocalTime;

public class TimeVariable implements IVariable {

    private LocalTime value;

    public TimeVariable() {}

    public TimeVariable(LocalTime value) {
        this.value = value;
    }

    public TimeVariable(String value) {
        this.value = LocalTime.parse(value);
    }

    @Override public EVariableType getType() {
        return EVariableType.TIME;
    }

    @Override
    public boolean equals(IVariable test) {
        TimeVariable numberVariable = getTimeVariable(test);
        return value.equals(numberVariable.value);
    }

    private TimeVariable getTimeVariable(IVariable test) {
        if (!(test instanceof TimeVariable)) throw new WrongVariableTypeException();

        return (TimeVariable) test;
    }

    @Override
    public boolean lt(IVariable test) {
        TimeVariable numberVariable = getTimeVariable(test);
        return value.isBefore(numberVariable.value);
    }

    @Override
    public boolean gt(IVariable test) {
        TimeVariable numberVariable = getTimeVariable(test);
        return value.isAfter(numberVariable.value);
    }

    @Override
    public boolean le(IVariable test) {
        TimeVariable numberVariable = getTimeVariable(test);
        return value.compareTo(numberVariable.value) <= 0;
    }

    @Override
    public boolean ge(IVariable test) {
        TimeVariable numberVariable = getTimeVariable(test);
        return value.compareTo(numberVariable.value) >= 0;
    }

    public LocalTime getValue() {
        return value;
    }

    public void setValue(LocalTime value) {
        this.value = value;
    }
}
