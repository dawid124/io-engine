package pl.webd.dawid124.ioengine.module.automation.timer.structure;

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

    public void run(ScheduledExecutorService scheduler, Map<String, IVariable> variable, Double time) {
        cancel();

        int timeInt = defaultTime;
        if (time != null) {
            timeInt = time.intValue();
        }

        scheduler.schedule(() -> blocks.forEach(b -> b.run(variable, zoneId)), timeInt, TimeUnit.MILLISECONDS);
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
