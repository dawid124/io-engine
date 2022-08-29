package pl.webd.dawid124.ioengine.mqtt.action.adapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;
import pl.webd.dawid124.ioengine.module.action.model.rest.Color;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;

import java.lang.reflect.Type;

public class IoActionJsonAdapter implements JsonSerializer<IoAction> {
    

    @Override
    public JsonElement serialize(IoAction ioAction, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty("id", ioAction.getIoId());



        if (EDeviceType.NEO.equals(ioAction.getIoType())) {

            Color color = ioAction.getColor();
            String colorStr = String.format("%02x%02x%02x%02x%02x", color.getR(), color.getG(), color.getB(), color.getW(), color.getWw());

            object.addProperty("an", ioAction.getAnimationId());
            object.addProperty("ssm", ioAction.getStaticSubModeId());
            object.addProperty("c", colorStr);
            object.addProperty("b", ioAction.getBrightness());
            object.addProperty("s", ioAction.getSpeed());
            if (ioAction.getStepTime() > 0) object.addProperty("st", ioAction.getStepTime());
            object.addProperty("t", ioAction.getTime());

        } else if (EDeviceType.RGBW.equals(ioAction.getIoType())
                || EDeviceType.CCT.equals(ioAction.getIoType())
                || EDeviceType.LED.equals(ioAction.getIoType())) {

            Color color = ioAction.getColor();
            String colorStr = String.format("%02x%02x%02x%02x%02x", color.getR(), color.getG(), color.getB(), color.getW(), color.getWw());

            if (ioAction.getStepTime() > 0) object.addProperty("st", ioAction.getStepTime());
            object.addProperty("t", ioAction.getTime());
            if (ioAction.getDelay() > 0) object.addProperty("d", ioAction.getDelay());
            object.addProperty("c", colorStr);
            object.addProperty("b", ioAction.getBrightness());

        } else if (EDeviceType.BLIND.equals(ioAction.getIoType())
                || EDeviceType.SWITCH.equals(ioAction.getIoType())) {

            object.addProperty("a", ioAction.getAction().toString());
            object.addProperty("t", ioAction.getTime());

        }

        return object;
    }
}
