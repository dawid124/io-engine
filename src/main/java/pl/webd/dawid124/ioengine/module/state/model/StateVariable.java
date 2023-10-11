package pl.webd.dawid124.ioengine.module.state.model;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

@Document(collection = "variable-state", schemaVersion= "1.0")
public class StateVariable {
    @Id
    private String id;

    private IVariable var;

    public StateVariable() {}

    public StateVariable(String id, IVariable var) {
        this.id = id;
        this.var = var;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IVariable getVar() {
        return var;
    }

    public void setVar(IVariable var) {
        this.var = var;
    }
}
