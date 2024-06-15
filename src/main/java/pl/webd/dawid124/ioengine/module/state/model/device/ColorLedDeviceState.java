package pl.webd.dawid124.ioengine.module.state.model.device;

import pl.webd.dawid124.ioengine.module.action.model.rest.Color;
import pl.webd.dawid124.ioengine.module.action.model.rest.IColor;

public class ColorLedDeviceState extends LedDeviceState {

    private IColor color;

    public ColorLedDeviceState() {}

    public ColorLedDeviceState(String id, String name) {
        super(id, name, EDeviceStateType.RGBW);
        this.color = new Color();
        this.brightness = 0;
    }

    public ColorLedDeviceState(String id, String name, IColor color, int brightness) {
        super(id, name, EDeviceStateType.RGBW);
        this.color = color;
        this.brightness = brightness;
    }

    public IColor getColor() {
        return color;
    }

    public void setColor(IColor color) {
        this.color = color;
    }
}
