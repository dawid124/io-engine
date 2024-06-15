package pl.webd.dawid124.ioengine.module.state.model.device;

import pl.webd.dawid124.ioengine.module.action.model.rest.Color;
import pl.webd.dawid124.ioengine.module.action.model.rest.IColor;

public class NeoDeviceState extends LedDeviceState {

    private IColor color;
    private IColor color2;
    private int animationId;
    private int speed;

    public NeoDeviceState() {}

    public NeoDeviceState(String id, String name) {
        super(id, name, EDeviceStateType.NEO);
        this.color = new Color();
        this.brightness = 0;
    }

    public IColor getColor() {
        return color;
    }

    public void setColor(IColor color) {
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

    public IColor getColor2() {
        return color2;
    }

    public void setColor2(IColor color2) {
        this.color2 = color2;
    }
}
