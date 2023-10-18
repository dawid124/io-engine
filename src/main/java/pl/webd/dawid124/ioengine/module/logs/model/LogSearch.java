package pl.webd.dawid124.ioengine.module.logs.model;

import pl.webd.dawid124.ioengine.module.state.model.device.EDeviceStateType;

public class LogSearch {
    private String ioId;
    private EDeviceStateType ioType;
    private long dateFrom;
    private long dateTo;

    public String getIoId() {
        return ioId;
    }

    public void setIoId(String ioId) {
        this.ioId = ioId;
    }

    public EDeviceStateType getIoType() {
        return ioType;
    }

    public void setIoType(EDeviceStateType ioType) {
        this.ioType = ioType;
    }

    public long getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(long dateFrom) {
        this.dateFrom = dateFrom;
    }

    public long getDateTo() {
        return dateTo;
    }

    public void setDateTo(long dateTo) {
        this.dateTo = dateTo;
    }
}
