package pl.webd.dawid124.ioengine.module.device.model.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import pl.webd.dawid124.ioengine.module.action.model.rest.Color;
import pl.webd.dawid124.ioengine.module.action.model.rest.IColor;
import pl.webd.dawid124.ioengine.module.action.model.server.ILedChangeData;
import pl.webd.dawid124.ioengine.module.action.model.server.LedChangeData;

import java.lang.reflect.Type;

public class ILedChangeDataJsonAdapter implements JsonDeserializer<ILedChangeData> {

    @Override
    public ILedChangeData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {

        return context.deserialize(jsonElement, LedChangeData.class);
    }
}
