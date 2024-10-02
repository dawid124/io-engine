package pl.webd.dawid124.ioengine.module.automation.macro.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import pl.webd.dawid124.ioengine.module.action.model.server.LedChangeData;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.automation.macro.block.condition.EConditionType;
import pl.webd.dawid124.ioengine.module.automation.macro.block.condition.ICondition;
import pl.webd.dawid124.ioengine.module.expresion.ExpressionProxyBuilder;

import java.lang.reflect.Type;

public class IConditionJsonAdapter implements JsonDeserializer<ICondition> {

    private final AutomationContext context;
    private static final String TYPE_ATTR = "type";
    private static final String ERROR_MSG = "Error on parsing ICondition Type, type: [%s]";

    public IConditionJsonAdapter(AutomationContext context) {
        this.context = context;
    }


    @Override
    public ICondition deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonElement messageType = getType(jsonElement);

        try {
            EConditionType conditionType = EConditionType.valueOf(messageType.getAsString());

//            return ExpressionProxyBuilder.instance(conditionType.getClazz(), context).deserialize(jsonElement, jsonDeserializationContext);
            return jsonDeserializationContext.deserialize(jsonElement, conditionType.getClazz());
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
