package pl.webd.dawid124.ioengine.module.state.model.device;

public class ZigbeeDeviceState extends DeviceState {

    private double battery;
    private double linkquality;

    public ZigbeeDeviceState() {}

    public ZigbeeDeviceState(String ioId, String name, EDeviceStateType ioType, double battery, double linkquality) {
        super(ioId, name, ioType);
        this.battery = battery;
        this.linkquality = linkquality;
    }

    public double getBattery() {
        return battery;
    }

    public void setBattery(double battery) {
        this.battery = battery;
    }

    public double getLinkquality() {
        return linkquality;
    }

    public void setLinkquality(double linkquality) {
        this.linkquality = linkquality;
    }
}
