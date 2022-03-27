package pl.webd.dawid124.ioengine.model;

import pl.webd.dawid124.ioengine.home.devices.output.EDeviceType;
import pl.webd.dawid124.ioengine.home.state.Color;

import java.io.Serializable;

public class Action implements Serializable {

    private String ioId;
    private String name;
    private EDeviceType ioType;
    private EActionType action;

    private Color color;
    private int brightness;

    private int speed;
    private int animationId;

    private int stepTime;
    private int time;

    private int percent;

    public String getIoId() {
        return ioId;
    }

    public void setIoId(String ioId) {
        this.ioId = ioId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EDeviceType getIoType() {
        return ioType;
    }

    public void setIoType(EDeviceType ioType) {
        this.ioType = ioType;
    }

    public EActionType getAction() {
        return action;
    }

    public void setAction(EActionType action) {
        this.action = action;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAnimationId() {
        return animationId;
    }

    public void setAnimationId(int animationId) {
        this.animationId = animationId;
    }

    public int getStepTime() {
        return stepTime;
    }

    public void setStepTime(int stepTime) {
        this.stepTime = stepTime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
