package pl.webd.dawid124.ioengine.module.state.model.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.zigbee.ZigbeeMessage;

import java.lang.reflect.Type;

public class ZigbeeMessageAdapter implements JsonDeserializer<ZigbeeMessage> {
    @Override
    public ZigbeeMessage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }
}
