package pl.webd.dawid124.ioengine.module.automation.macro.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import pl.webd.dawid124.ioengine.module.state.model.variable.EVariableType;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.lang.reflect.Type;

public class IVariableJsonAdapter implements JsonDeserializer<IVariable> {

    private static final String TYPE_ATTR = "type";

    private static final String ERROR_MSG = "Error on parsing IVariable Type, content: [%s]";

    @Override
    public IVariable deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonElement messageType = getType(jsonElement);

        try {
            EVariableType blockType = EVariableType.valueOf(messageType.getAsString());
            return context.deserialize(jsonElement, blockType.getClazz());
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
