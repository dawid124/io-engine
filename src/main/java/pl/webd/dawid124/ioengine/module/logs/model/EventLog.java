package pl.webd.dawid124.ioengine.module.logs.model;

import pl.webd.dawid124.ioengine.module.state.model.device.EDeviceStateType;

import java.util.Map;

public class EventLog {
    private String id;
    private long time;
    private EDeviceStateType ioType;
    private Map<String, Object> msg;

    public EventLog(String id, long time, String ioType, Map<String, Object> msg) {
        this.time = time;
        this.id = id;
        this.ioType = EDeviceStateType.valueOf(ioType);
        this.msg = msg;
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
