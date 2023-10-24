package pl.webd.dawid124.ioengine.module.state.model.device;

import pl.webd.dawid124.ioengine.module.action.model.rest.EActionType;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;

public class SwitchDeviceState extends DeviceState {

    private boolean on;

    public SwitchDeviceState() {}

    public SwitchDeviceState(String ioId, String name, EDeviceStateType ioType, boolean on) {
        super(ioId, name, ioType);
        this.on = on;
    }

    public void update(IoAction a) {
        if (EActionType.ON.equals(a.getAction())) {
            on = true;
        } else if (EActionType.OFF.equals(a.getAction())) {
            on = false;
        } else if (EActionType.TEMP_ON.equals(a.getAction())) {
            on = true;
        }
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
