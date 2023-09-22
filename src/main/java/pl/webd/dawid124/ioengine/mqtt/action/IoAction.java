package pl.webd.dawid124.ioengine.mqtt.action;

import pl.webd.dawid124.ioengine.module.action.model.rest.EActionType;
import pl.webd.dawid124.ioengine.module.device.model.driver.instance.EIoDriverType;
import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;
import pl.webd.dawid124.ioengine.module.action.model.rest.Color;

public class IoAction {

    private String ioId;

    private EDeviceType ioType;

    private EActionType action;

    private Color color;
    private Color color2;

    private String zigbeeAction;
    private int brightness;

    private int speed;
    private int animationId;
    private int staticSubModeId;

    private int stepTime;
    private int time;
    private int delay;

    private int intParam;

    private String deviceId;
    private EIoDriverType deviceType;

    public IoAction() {}

    public IoAction(String ioId, EDeviceType ioType, EActionType action, Color color,
                    int brightness, int delay, int time, int stepTime,
                    String deviceId, EIoDriverType deviceType, String zigbeeAction) {
        this.ioId = ioId;
        this.ioType = ioType;
        this.action = action;
        this.color = color;
        this.brightness = brightness;
        this.delay = delay;
        this.time = time;
        this.stepTime = stepTime;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.zigbeeAction = zigbeeAction;
    }

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

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getIntParam() {
        return intParam;
    }

    public void setIntParam(int intParam) {
        this.intParam = intParam;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IoAction action1 = (IoAction) o;

        if (brightness != action1.brightness) return false;
        if (speed != action1.speed) return false;
        if (animationId != action1.animationId) return false;
        if (staticSubModeId != action1.staticSubModeId) return false;
        if (stepTime != action1.stepTime) return false;
        if (time != action1.time) return false;
        if (delay != action1.delay) return false;
        if (intParam != action1.intParam) return false;
        if (ioId != null ? !ioId.equals(action1.ioId) : action1.ioId != null) return false;
        if (ioType != action1.ioType) return false;
        if (action != action1.action) return false;
        if (color != null ? !color.equals(action1.color) : action1.color != null) return false;
        if (color2 != null ? !color2.equals(action1.color2) : action1.color2 != null) return false;
        if (deviceId != null ? !deviceId.equals(action1.deviceId) : action1.deviceId != null) return false;
        return deviceType == action1.deviceType;
    }

    @Override
    public int hashCode() {
        int result = ioId != null ? ioId.hashCode() : 0;
        result = 31 * result + (ioType != null ? ioType.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (color2 != null ? color2.hashCode() : 0);
        result = 31 * result + brightness;
        result = 31 * result + speed;
        result = 31 * result + animationId;
        result = 31 * result + staticSubModeId;
        result = 31 * result + stepTime;
        result = 31 * result + time;
        result = 31 * result + delay;
        result = 31 * result + intParam;
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (deviceType != null ? deviceType.hashCode() : 0);
        return result;
    }

    public String getZigbeeAction() {
        return zigbeeAction;
    }

    public void setZigbeeAction(String zigbeeAction) {
        this.zigbeeAction = zigbeeAction;
    }
}
