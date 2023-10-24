package pl.webd.dawid124.ioengine.module.action.model.rest;

import pl.webd.dawid124.ioengine.module.device.model.driver.instance.EIoDriverType;
import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;

import java.io.Serializable;

public class UiAction implements IUiAction {

    private String ioId;
    private String name;
    private EDeviceType ioType;
    private EActionType action;

    private Color color;
    private Color color2;
    private int brightness;

    private int speed;
    private int animationId;

    private int stepTime;
    private int time;
    private int delay;

    private int percent;

    private boolean group;

    private String zigbeeAction;

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

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public boolean isBlind() {
        return EDeviceType.BLIND.equals(ioType);
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String getZigbeeAction() {
        return zigbeeAction;
    }

    public void setZigbeeAction(String zigbeeAction) {
        this.zigbeeAction = zigbeeAction;
    }

    public IoAction toIoAction(IDevice device) {
        String deviceId = device.getDriverConfiguration().getDriver().getId();
        EIoDriverType deviceType = device.getDriverConfiguration().getDriver().getType();
        IoAction ioAction = new IoAction(
                this.getIoId(),
                device.getIoType(),
                this.getAction(),
                this.getColor(),
                this.getBrightness(),
                this.getDelay(),
                this.getTime(),
                this.getStepTime(),
                deviceId,
                deviceType,
                this.getZigbeeAction());
        return ioAction;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

}
