package pl.webd.dawid124.ioengine.module.structure.model;

import java.util.List;

public class TemperatureScenes {

    private String id;
    private String name;
    private boolean lock;
    private List<TemperatureRange> ranges;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TemperatureRange> getRanges() {
        return ranges;
    }

    public void setRanges(List<TemperatureRange> ranges) {
        this.ranges = ranges;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
}
