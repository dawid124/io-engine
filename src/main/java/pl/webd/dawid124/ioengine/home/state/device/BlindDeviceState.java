package pl.webd.dawid124.ioengine.home.state.device;

public class BlindDeviceState extends DeviceState {

    private EBlindDirection position;
    private int dimmerPercent;

    public BlindDeviceState(String id, String name) {
        super(id, name);
        this.position = EBlindDirection.UP;
        this.dimmerPercent = 0;
    }

    public BlindDeviceState(String id, String name, EBlindDirection position, int dimmerPercent) {
        super(id, name);
        this.position = position;
        this.dimmerPercent = dimmerPercent;
    }

    public EBlindDirection getPosition() {
        return position;
    }

    public int getDimmerPercent() {
        return dimmerPercent;
    }

    public void setPosition(EBlindDirection position) {
        this.position = position;
    }

    public void setDimmerPercent(int dimmerPercent) {
        this.dimmerPercent = dimmerPercent;
    }
}
