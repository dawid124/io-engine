package pl.webd.dawid124.ioengine.module.automation.cron;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;

import javax.annotation.PreDestroy;

@Service
public class CronService {

    private final ThreadPoolTaskScheduler scheduler;

    private final AutomationContext context;

    private CronStructure cronStructure;

    public CronService(AutomationContext context) {
        this.context = context;

        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        scheduler.initialize();
    }

    @PreDestroy
    public void destructor() {
        scheduler.shutdown();
    }

    public CronStructure getCronStructure() {
        return cronStructure;
    }

    public void setCronStructure(CronStructure cronStructure) {
        this.cronStructure = cronStructure;
        cronStructure.getCrons().values().forEach(c -> c.run(context, scheduler));
    }
}
