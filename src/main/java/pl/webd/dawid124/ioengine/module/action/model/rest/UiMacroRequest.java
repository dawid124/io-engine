package pl.webd.dawid124.ioengine.module.action.model.rest;

import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.io.Serializable;
import java.util.Map;

public class UiMacroRequest implements Serializable {

    private String id;
    private Map<String, IVariable> variable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, IVariable> getVariable() {
        return variable;
    }

    public void setVariable(Map<String, IVariable> variable) {
        this.variable = variable;
    }
}
