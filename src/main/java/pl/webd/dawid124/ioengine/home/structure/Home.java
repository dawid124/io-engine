package pl.webd.dawid124.ioengine.home.structure;

import pl.webd.dawid124.ioengine.home.state.variable.IVariable;

import java.util.HashMap;
import java.util.Map;

public class Home {

    private final HashMap<String, IVariable> variables;
    private final HashMap<String, Zone> zones;

    public Home() {
        this.variables = new HashMap<>();
        this.zones = new HashMap<>();
    }

    public Map<String, IVariable> getVariables() {
        return variables;
    }

    public Map<String, Zone> getZones() {
        return zones;
    }
}
