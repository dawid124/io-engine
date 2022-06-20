package pl.webd.dawid124.ioengine.module.automation.timer.structure;

import java.util.HashMap;
import java.util.Map;

public class TimerStructure {

    private final Map<String, Timer> timers;

    public TimerStructure() {
        this.timers = new HashMap<>();
    }

    public Map<String, Timer> getTimers() {
        return timers;
    }
}
