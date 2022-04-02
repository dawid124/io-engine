package pl.webd.dawid124.ioengine.module.structure.model;

import java.util.HashMap;
import java.util.Map;

public class Home {

    private final HashMap<String, Zone> zones;

    public Home() {
        this.zones = new HashMap<>();
    }

    public Map<String, Zone> getZones() {
        return zones;
    }
}
