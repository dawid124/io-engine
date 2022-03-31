package pl.webd.dawid124.ioengine.home.state.device;

import pl.webd.dawid124.ioengine.home.devices.output.EDeviceType;
import pl.webd.dawid124.ioengine.model.IoAction;

public class BlindDeviceState extends DeviceState {

    private EBlindDirection position;
    private int dimmerPercent;

    public BlindDeviceState(String id) {
        super(id, EDeviceStateType.BLIND);
        this.position = EBlindDirection.UP;
        this.dimmerPercent = 0;
    }

    public BlindDeviceState(String id, EBlindDirection position, int dimmerPercent) {
        super(id, EDeviceStateType.BLIND);
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

    @Override
    public IoAction toAction() {
        return null;
    }
}
