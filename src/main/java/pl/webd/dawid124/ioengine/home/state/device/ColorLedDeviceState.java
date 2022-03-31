package pl.webd.dawid124.ioengine.home.state.device;

import pl.webd.dawid124.ioengine.home.state.Color;

public class ColorLedDeviceState extends LedDeviceState {

    private Color color;

    public ColorLedDeviceState(String id) {
        super(id, EDeviceStateType.COLOR_LED);
        this.color = new Color();
        this.brightness = 0;
    }

    public ColorLedDeviceState(String id,  Color color, int brightness) {
        super(id, EDeviceStateType.COLOR_LED);
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
