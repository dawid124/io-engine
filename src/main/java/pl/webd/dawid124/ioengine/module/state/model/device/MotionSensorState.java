package pl.webd.dawid124.ioengine.module.state.model.device;

import java.time.LocalDateTime;
import java.util.Date;

public class MotionSensorState extends DeviceState {

    private boolean state;

    private LocalDateTime lastActiveDate;
    private LocalDateTime lastDeActiveDate;

    public MotionSensorState(String ioId, String name, EDeviceStateType ioType, boolean state) {
        super(ioId, name, ioType);
        this.state = state;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public LocalDateTime getLastActiveDate() {
        return lastActiveDate;
    }

    public void setLastActiveDate(LocalDateTime lastActiveDate) {
        this.lastActiveDate = lastActiveDate;
    }

    public LocalDateTime getLastInactiveDate() {
        return lastDeActiveDate;
    }

    public void setLastDeActiveDate(LocalDateTime lastDeActiveDate) {
        this.lastDeActiveDate = lastDeActiveDate;
    }
}
