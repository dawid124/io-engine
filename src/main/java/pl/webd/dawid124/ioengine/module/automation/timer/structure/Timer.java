package pl.webd.dawid124.ioengine.module.automation.timer.structure;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.automation.macro.block.IBlock;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Timer {

    private ScheduledFuture<?> future;

    private String zoneId;
    private String id;

    private int defaultTime;

    private List<IBlock> blocks;

    public Timer() {}

    public Timer(String zoneId, String id, int defaultTime, List<IBlock> blocks) {
        this.zoneId = zoneId;
        this.id = id;
        this.defaultTime = defaultTime;
        this.blocks = blocks;
    }

    public void run(AutomationContext context, ScheduledExecutorService scheduler,
                    Map<String, IVariable> variable, Integer time) {
        cancel();

        if (time == null) time = defaultTime;

        future = scheduler.schedule(() -> blocks.forEach(b -> b.run(context, variable, zoneId)), time, TimeUnit.MILLISECONDS);
    }

    public void cancel() {
        if (future != null) future.cancel(false);
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDefaultTime() {
        return defaultTime;
    }

    public void setDefaultTime(int defaultTime) {
        this.defaultTime = defaultTime;
    }

    public List<IBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<IBlock> blocks) {
        this.blocks = blocks;
    }

    public ScheduledFuture<?> getFuture() {
        return future;
    }

    public void setFuture(ScheduledFuture<?> future) {
        this.future = future;
    }
}
