package pl.webd.dawid124.ioengine.module.state.model.rest;

import pl.webd.dawid124.ioengine.module.state.model.StateVariable;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ZonesStateResponse implements Serializable {

    private Map<String, ZoneStateResponse> zones;
    private Map<String, StateVariable> variables;
    private Map<String, DeviceState> sensors;

    public ZonesStateResponse() {
        this.zones = new HashMap<>();
    }

    public Map<String, ZoneStateResponse> getZones() {
        return zones;
    }

    public void setZones(Map<String, ZoneStateResponse> zones) {
        this.zones = zones;
    }

    public Map<String, StateVariable> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, StateVariable> variables) {
        this.variables = variables;
    }

    public void setSensors(Map<String, DeviceState> sensors) {
        this.sensors = sensors;
    }

    public Map<String, DeviceState> getSensors() {
        return sensors;
    }
}
