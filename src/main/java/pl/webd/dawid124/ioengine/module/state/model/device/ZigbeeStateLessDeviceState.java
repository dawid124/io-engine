package pl.webd.dawid124.ioengine.module.state.model.device;

public class ZigbeeStateLessDeviceState extends ZigbeeDeviceState {

    public ZigbeeStateLessDeviceState() {}

    public ZigbeeStateLessDeviceState(String ioId, String name, EDeviceStateType ioType) {
        super(ioId, name, ioType, 0 ,0);
    }

    public ZigbeeStateLessDeviceState(String ioId, String name, EDeviceStateType ioType, double battery, double linkquality) {
        super(ioId, name, ioType, battery, linkquality);
    }
}
