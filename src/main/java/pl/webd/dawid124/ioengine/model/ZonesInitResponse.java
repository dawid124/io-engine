package pl.webd.dawid124.ioengine.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ZonesInitResponse implements Serializable {

    private Map<String, ZoneInitResponse> zones;

    public ZonesInitResponse() {
        this.zones = new HashMap<>();
    }

    public Map<String, ZoneInitResponse> getZones() {
        return zones;
    }

    public void setZones(Map<String, ZoneInitResponse> zones) {
        this.zones = zones;
    }
}
