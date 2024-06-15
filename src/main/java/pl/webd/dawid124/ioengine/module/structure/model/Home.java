package pl.webd.dawid124.ioengine.module.structure.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.webd.dawid124.ioengine.module.automation.macro.block.runner.ConditionVariableRunner;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Home {

    private final LinkedHashMap<String, Zone> zones;
    private final LinkedHashMap<String, Macro> downBlindMacros;
    @JsonIgnore
    private final LinkedHashMap<String, ButtonGroup> rightBlindGroups;
    private final Map<String, IVariable> variables;
    @JsonIgnore
    private final Map<String, ConditionVariableRunner> conditionVariables;

    public Home() {
        this.zones = new LinkedHashMap<>();
        this.downBlindMacros = new LinkedHashMap<>();
        this.rightBlindGroups = new LinkedHashMap<>();
        this.variables = new LinkedHashMap<>();
        this.conditionVariables = new LinkedHashMap<>();
    }

    public Map<String, Zone> getZones() {
        return zones;
    }

    public HashMap<String, Macro> getDownBlindMacros() {
        return downBlindMacros;
    }

    public LinkedHashMap<String, ButtonGroup> getRightBlindGroups() {
        return rightBlindGroups;
    }

    public Map<String, IVariable> getVariables() {
        return variables;
    }

    public Map<String, ConditionVariableRunner> getConditionVariables() {
        return conditionVariables;
    }
}
