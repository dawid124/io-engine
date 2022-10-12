package pl.webd.dawid124.ioengine.module.automation.cron;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.automation.macro.block.IBlock;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public class CronTask {

    private String id;

    private String zoneId;

    private String cron;

    private Map<String, IVariable> variable;

    private ScheduledFuture<?> future;

    private List<IBlock> blocks;

    public void run(AutomationContext context, ThreadPoolTaskScheduler scheduler) {
        future = scheduler.schedule(() -> blocks.forEach(b -> b.run(context, variable, zoneId)), new CronTrigger(cron));
    }

    public void cancel() {
        if (future != null) future.cancel(false);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public List<IBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<IBlock> blocks) {
        this.blocks = blocks;
    }

    public Map<String, IVariable> getVariable() {
        return variable;
    }

    public void setVariable(Map<String, IVariable> variable) {
        this.variable = variable;
    }
}
