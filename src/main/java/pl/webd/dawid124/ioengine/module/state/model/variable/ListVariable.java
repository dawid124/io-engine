package pl.webd.dawid124.ioengine.module.state.model.variable;

import pl.webd.dawid124.ioengine.module.automation.macro.exception.NotSupportedConditionException;

import java.util.List;

public class ListVariable implements IVariable {

    private List<IVariable> value;

    @Override public EVariableType getType() {
        return EVariableType.LIST;
    }

    @Override
    public boolean equals(IVariable test) {
        throw new NotSupportedConditionException();
    }

    public List<IVariable> getValue() {
        return value;
    }

    public void setValue(List<IVariable> value) {
        this.value = value;
    }
}
