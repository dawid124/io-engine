package pl.webd.dawid124.ioengine.module.state.model.rest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ZonesStateResponse implements Serializable {

    private Map<String, ZoneStateResponse> zones;

    public ZonesStateResponse() {
        this.zones = new HashMap<>();
    }

    public Map<String, ZoneStateResponse> getZones() {
        return zones;
    }

    public void setZones(Map<String, ZoneStateResponse> zones) {
        this.zones = zones;
    }
}
