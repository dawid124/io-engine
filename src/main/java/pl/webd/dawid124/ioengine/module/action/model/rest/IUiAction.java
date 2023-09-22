package pl.webd.dawid124.ioengine.module.action.model.rest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;
import pl.webd.dawid124.ioengine.module.device.model.output.IDevice;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;

import java.io.Serializable;

@JsonSerialize(as = UiAction.class)
@JsonDeserialize(as = UiAction.class)
public interface IUiAction extends Serializable {
    String getIoId();

    void setIoId(String ioId);

    String getName();

    void setName(String name);

    EDeviceType getIoType();

    void setIoType(EDeviceType ioType);

    EActionType getAction();

    void setAction(EActionType action);

    Color getColor();

    void setColor(Color color);

    int getBrightness();

    void setBrightness(int brightness);

    int getSpeed();

    void setSpeed(int speed);

    int getAnimationId();

    void setAnimationId(int animationId);

    int getStepTime();

    void setStepTime(int stepTime);

    int getTime();

    void setTime(int time);

    int getPercent();

    void setPercent(int percent);

    boolean isGroup();

    void setGroup(boolean group);

    boolean isBlind();

    int getDelay();

    void setDelay(int delay);

    IoAction toIoAction(IDevice device);

    Color getColor2();

    void setColor2(Color color2);

    String getZigbeeAction();

    void setZigbeeAction(String zigbeeAction);
}
