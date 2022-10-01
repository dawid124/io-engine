package pl.webd.dawid124.ioengine.module.state.model.rest;

import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ZonesStateResponse implements Serializable {

    private Map<String, ZoneStateResponse> zones;

    private Map<String, IVariable> variables;

    public ZonesStateResponse() {
        this.zones = new HashMap<>();
    }

    public Map<String, ZoneStateResponse> getZones() {
        return zones;
    }

    public void setZones(Map<String, ZoneStateResponse> zones) {
        this.zones = zones;
    }

    public Map<String, IVariable> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, IVariable> variables) {
        this.variables = variables;
    }
}
