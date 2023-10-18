package pl.webd.dawid124.ioengine.module.logs.model;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.EDeviceStateType;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

@Document(collection = "event-log", schemaVersion= "1.0")
public class EventLog {

    @Id
    private String dbId;
    private String id;
    private long time;
    private EDeviceStateType ioType;
    private Map<String, Object> msg;

    public EventLog() {
        this.time = Timestamp.from(Instant.now()).getTime();
    }

    public EventLog(DeviceState state, Map<String, Object> msg) {
        this.time = Timestamp.from(Instant.now()).getTime();
        this.dbId = state.getIoId() + this.time;
        this.id = state.getIoId();
        this.ioType = state.getIoType();
        this.msg = msg;
    }


    public EventLog(String id, EDeviceStateType ioType, Map<String, Object> msg) {
        this.time = Timestamp.from(Instant.now()).getTime();
        this.dbId = id + this.time;
        this.id = id;
        this.ioType = ioType;
        this.msg = msg;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public EDeviceStateType getIoType() {
        return ioType;
    }

    public void setIoType(EDeviceStateType ioType) {
        this.ioType = ioType;
    }

    public Map<String, Object> getMsg() {
        return msg;
    }

    public void setMsg(Map<String, Object> msg) {
        this.msg = msg;
    }
}
