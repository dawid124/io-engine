package pl.webd.dawid124.ioengine.module.state.model.device;

import pl.webd.dawid124.ioengine.module.action.model.rest.Color;

public class NeoDeviceState extends LedDeviceState {

    private Color color;
    private Color color2;
    private int animationId;
    private int speed;

    public NeoDeviceState(String id, String name) {
        super(id, name, EDeviceStateType.NEO);
        this.color = new Color();
        this.brightness = 0;
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

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }
}
