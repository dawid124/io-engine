package pl.webd.dawid124.ioengine.home.state.device;

import pl.webd.dawid124.ioengine.home.state.Color;

public class ColorLedDeviceState extends LedDeviceState {

    private Color color;

    public ColorLedDeviceState(String id, String name) {
        super(id, name, EDeviceStateType.RGBW);
        this.color = new Color();
        this.brightness = 0;
    }

    public ColorLedDeviceState(String id, String name, Color color, int brightness) {
        super(id, name, EDeviceStateType.RGBW);
        this.color = color;
        this.brightness = brightness;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
