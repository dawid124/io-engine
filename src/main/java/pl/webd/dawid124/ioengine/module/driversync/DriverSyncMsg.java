package pl.webd.dawid124.ioengine.module.driversync;

public class DriverSyncMsg {

    private String id;

    private EDriverSyncType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EDriverSyncType getType() {
        return type;
    }

    public void setType(EDriverSyncType type) {
        this.type = type;
    }
}
