package pl.webd.dawid124.ioengine.home.state.device;

import pl.webd.dawid124.ioengine.home.state.Color;

public class NeoDeviceState extends LedDeviceState {

    private Color color;
    private int animationId;
    private int speed;

    public NeoDeviceState(String id, String name) {
        super(id, name, EDeviceStateType.NEO);
        this.color = new Color();
        this.brightness = 0;
    }

    public NeoDeviceState(String id, String name, Color color, int brightness, int animationId, int speed) {
        super(id, name, EDeviceStateType.NEO);
        this.color = color;
        this.brightness = brightness;
        this.animationId = animationId;
        this.speed = speed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getAnimationId() {
        return animationId;
    }

    public void setAnimationId(int animationId) {
        this.animationId = animationId;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
