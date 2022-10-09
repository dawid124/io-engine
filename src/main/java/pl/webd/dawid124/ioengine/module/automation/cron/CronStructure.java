package pl.webd.dawid124.ioengine.module.automation.cron;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CronStructure implements Serializable {

    private final Map<String, CronTask> crons;

    public CronStructure() {
        this.crons = new HashMap<>();
    }

    public Map<String, CronTask> getCrons() {
        return crons;
    }
}
