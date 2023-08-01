package pl.webd.dawid124.ioengine.module.automation.timer.structure;

import pl.webd.dawid124.ioengine.module.automation.macro.block.IBlock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimerStructure {

    private final Map<String, Timer> timers;

    public TimerStructure() {
        this.timers = new HashMap<>();
    }

    public Map<String, Timer> getTimers() {
        return timers;
    }

    public Timer createTimer(String id, String zoneId, List<IBlock> blocks) {
        Timer timer = new Timer(zoneId, id, 0, blocks);
        timers.put(id, timer);
        return timer;
    }
}
