package pl.webd.dawid124.ioengine.module.state.model.device;

public class ZigbeeSwitchDeviceState extends ZigbeeDeviceState {

    private boolean on;
    private double power;

    public ZigbeeSwitchDeviceState() {}

    public ZigbeeSwitchDeviceState(String ioId, String name, EDeviceStateType ioType, boolean on) {
        super(ioId, name, ioType, 0 ,0);
        this.on = on;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }
}
