package pl.webd.dawid124.ioengine.module.automation.macro.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import pl.webd.dawid124.ioengine.module.automation.macro.block.condition.EConditionType;
import pl.webd.dawid124.ioengine.module.automation.macro.block.condition.ICondition;

import java.lang.reflect.Type;

public class IConditionJsonAdapter implements JsonDeserializer<ICondition> {

    private static final String TYPE_ATTR = "type";
    private static final String ERROR_MSG = "Error on parsing ICondition Type, type: [%s]";


    @Override
    public ICondition deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonElement messageType = getType(jsonElement);

        try {
            EConditionType conditionType = EConditionType.valueOf(messageType.getAsString());
            return context.deserialize(jsonElement, conditionType.getClazz());
        } catch (Exception e) {
            throw new JsonParseException(String.format(ERROR_MSG, jsonElement), e);
        }
    }

    private JsonElement getType(JsonElement jsonElement) {
        JsonElement messageType = jsonElement.getAsJsonObject().get(TYPE_ATTR);

        if (messageType == null || messageType.getAsString().length() == 0) {
            throw new JsonParseException(String.format(ERROR_MSG, jsonElement));
        }

        return messageType;
    }
}
