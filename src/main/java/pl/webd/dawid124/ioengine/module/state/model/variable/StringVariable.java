package pl.webd.dawid124.ioengine.module.state.model.variable;

import pl.webd.dawid124.ioengine.module.automation.macro.exception.WrongVariableTypeException;

import java.util.List;

public class StringVariable implements IVariable {

    private String value;

    public StringVariable(String value) {
        this.value = value;
    }

    @Override
    public EVariableType getType() {
        return EVariableType.STRING;
    }

    @Override
    public boolean equals(IVariable test) {
        if (!(test instanceof StringVariable)) throw new WrongVariableTypeException();

        StringVariable testStr = (StringVariable) test;
        return value.equals(testStr.value);
    }

    public boolean in(List<IVariable> test) {
        for (IVariable var: test) {
            if (this.equals(var)) {
                return true;
            }
        }
        return false;
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
