package pl.webd.dawid124.ioengine.module.automation.trigger;

import java.util.HashMap;
import java.util.Map;

public class TriggerStructure {
    private Map<String, Trigger> triggers;

    public TriggerStructure() {
        triggers = new HashMap<>();
    }

    public Map<String, Trigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(Map<String, Trigger> triggers) {
        this.triggers = triggers;
    }
}
