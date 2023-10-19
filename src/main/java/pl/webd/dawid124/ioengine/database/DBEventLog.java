package pl.webd.dawid124.ioengine.database;

import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;

import java.sql.Timestamp;
import java.time.Instant;

public class DBEventLog {
    private final String ioId;
    private final long time;
    private final String ioType;
    private final String msg;

    public DBEventLog(DeviceState state, String msg) {
        this.time = Timestamp.from(Instant.now()).getTime();
        this.ioId = state.getIoId();
        this.ioType = state.getIoType().name();
        this.msg = msg;
    }

    public DBEventLog(String ioId, long time, String ioType, String msg) {
        this.ioId = ioId;
        this.time = time;
        this.ioType = ioType;
        this.msg = msg;
    }
    public String getIoId() {
        return ioId;
    }

    public long getTime() {
        return time;
    }

    public String getIoType() {
        return ioType;
    }

    public String getMsg() {
        return msg;
    }

}
