package pl.webd.dawid124.ioengine.module.automation.cron;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class CronService {

    private final ThreadPoolTaskScheduler scheduler;

    private CronStructure cronStructure;

    public CronService() {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        scheduler.initialize();
    }

    public CronStructure getCronStructure() {
        return cronStructure;
    }

    public void setCronStructure(CronStructure cronStructure) {
        this.cronStructure = cronStructure;
        cronStructure.getCrons().values().forEach(c -> c.run(scheduler));
    }
}
