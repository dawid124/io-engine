package pl.webd.dawid124.ioengine.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ZonesResponse implements Serializable {

    private Map<String, ZoneResponse> zones;

    public ZonesResponse() {
        this.zones = new HashMap<>();
    }

    public Map<String, ZoneResponse> getZones() {
        return zones;
    }

    public void setZones(Map<String, ZoneResponse> zones) {
        this.zones = zones;
    }
}
