package pl.webd.dawid124.ioengine.module.state.model.device;

public class MotionSensorState extends DeviceState {

    private boolean state;

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
}
