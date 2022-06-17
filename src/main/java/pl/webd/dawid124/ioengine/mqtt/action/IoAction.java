package pl.webd.dawid124.ioengine.mqtt.action;

import pl.webd.dawid124.ioengine.module.action.model.rest.EActionType;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.EIoDriverType;
import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;
import pl.webd.dawid124.ioengine.module.share.Color;

public class IoAction {

    private String ioId;

    private EDeviceType ioType;

    private EActionType action;

    private Color color;
    private int brightness;

    private int speed;
    private int animationId;
    private int staticSubModeId;

    private int stepTime;
    private int time;

    private String deviceId;
    private EIoDriverType deviceType;

    public String getIoId() {
        return ioId;
    }

    public void setIoId(String ioId) {
        this.ioId = ioId;
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public EIoDriverType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(EIoDriverType deviceType) {
        this.deviceType = deviceType;
    }

    public int getStaticSubModeId() {
        return staticSubModeId;
    }

    public void setStaticSubModeId(int staticSubModeId) {
        this.staticSubModeId = staticSubModeId;
    }
}
