package pl.webd.dawid124.ioengine.home.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import pl.webd.dawid124.ioengine.home.devices.output.EDeviceType;
import pl.webd.dawid124.ioengine.home.state.device.DeviceState;
import pl.webd.dawid124.ioengine.home.state.device.EDeviceStateType;

import java.lang.reflect.Type;

public class DeviceStateJsonAdapter implements JsonDeserializer<DeviceState> {

    private static final String TYPE_ATTR = "ioType";

    private static final String ERROR_MSG = "Error on parsing Device State Type, type: [%s]";

    @Override
    public DeviceState deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {

        JsonElement messageType = getType(jsonElement);

        try {
            EDeviceStateType stateType = EDeviceStateType.valueOf(messageType.getAsString());
            return context.deserialize(jsonElement, stateType.getClazz());
        } catch (Exception e) {
            throw new JsonParseException(String.format(ERROR_MSG, jsonElement), e);
        }
    }

    private JsonElement getType(JsonElement jsonElement) {
        JsonElement messageType = jsonElement.getAsJsonObject().get(TYPE_ATTR);
        if (messageType == null) {
            messageType = jsonElement.getAsJsonObject().get(TYPE_ATTR.toLowerCase());
        }

        if (messageType == null || messageType.getAsString().length() == 0) {
            throw new JsonParseException(String.format(ERROR_MSG, jsonElement));
        }

        return messageType;
    }
}
