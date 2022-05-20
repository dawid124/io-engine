package pl.webd.dawid124.ioengine.module.action.model.server;

import pl.webd.dawid124.ioengine.module.state.model.device.LedDeviceState;
import pl.webd.dawid124.ioengine.mqtt.IoActionFactory;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;

import java.util.ArrayList;
import java.util.List;

public class ServerAction {

    private static final int MAX_LED_VALUE = 255;

    protected final ServerDevice device;

    public ServerAction(ServerDevice device) {
        this.device = device;
    }

    public List<IoAction> toIoActions() {
        List<IoAction> list = new ArrayList<>();
        if (device.isGroup()) {
            list.addAll(toGroupIoActions());
        } else {
            list.add(toStandardIoActions(device));
        }

        return list;
    }

    protected IoAction toStandardIoActions(ServerDevice serverDevice) {
        IoAction ioAction = IoActionFactory.fromDeviceState(serverDevice.getDevice(), serverDevice.getState());
        if (serverDevice.getParent() != null) {
            divideBrightnessByGroup(ioAction, ((LedDeviceState) serverDevice.getParent().getState()).getBrightness());
        }
        return ioAction;
    }

    protected List<IoAction> toGroupIoActions() {
        ArrayList<IoAction> actions = new ArrayList<>();

        for (ServerDevice child: device.getChildren()) {
            actions.add(toStandardIoActions(child));
        }

        return actions;
    }

    protected void divideBrightnessByGroup(IoAction action, int groupBrightness) {
        if (groupBrightness < MAX_LED_VALUE) {
            double percent = (double) groupBrightness / (double) MAX_LED_VALUE;
            double newValue = action.getBrightness() * percent;
            action.setBrightness((int) newValue);
        }
    }

    public ServerDevice getDevice() {
        return device;
    }
}
