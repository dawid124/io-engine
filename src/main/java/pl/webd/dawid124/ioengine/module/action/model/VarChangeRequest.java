package pl.webd.dawid124.ioengine.module.action.model;

import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.io.Serializable;

public class VarChangeRequest implements Serializable {

    private String zoneId;

    private String name;

    private IVariable value;

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IVariable getValue() {
        return value;
    }

    public void setValue(IVariable value) {
        this.value = value;
    }
}
