package pl.webd.dawid124.ioengine.module.state.model.variable;

import pl.webd.dawid124.ioengine.module.automatization.exception.NotSupportedConditionException;
import pl.webd.dawid124.ioengine.module.automatization.exception.WrongVariableTypeException;

public class StringVariable implements IVariable {

    private String value;

    public StringVariable(String value) {
        this.value = value;
    }

    @Override public EVariableType getType() {
        return EVariableType.STRING;
    }

    @Override
    public boolean equals(IVariable test) {
        if (!(test instanceof StringVariable)) throw new WrongVariableTypeException();

        StringVariable testStr = (StringVariable) test;
        return value.equals(testStr.value);
    }

    @Override
    public boolean contains(IVariable test) {
        if (!(test instanceof StringVariable)) throw new WrongVariableTypeException();

        StringVariable testStr = (StringVariable) test;
        return value.contains(testStr.value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
