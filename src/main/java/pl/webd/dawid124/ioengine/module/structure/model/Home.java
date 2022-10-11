package pl.webd.dawid124.ioengine.module.structure.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Home {

    private final LinkedHashMap<String, Zone> zones;
    private final LinkedHashMap<String, Macro> downBlindMacros;

    public Home() {
        this.zones = new LinkedHashMap<>();
        this.downBlindMacros = new LinkedHashMap<>();
    }

    public Map<String, Zone> getZones() {
        return zones;
    }

    public HashMap<String, Macro> getDownBlindMacros() {
        return downBlindMacros;
    }
}
