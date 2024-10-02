package pl.webd.dawid124.ioengine.module.automation.macro.json;

import com.google.gson.*;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.expresion.ExpressionProxy;
import pl.webd.dawid124.ioengine.module.expresion.ExpressionProxyBuilder;
import pl.webd.dawid124.ioengine.module.state.model.variable.EVariableType;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.TimeVariable;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.Map;

import static pl.webd.dawid124.ioengine.module.expresion.ExpressionProxyBuilder.getExpressionsAndRemoveItFromPrimitiveTypes;

public class ExpressionIVariableJsonAdapter implements JsonDeserializer<IVariable> {

    private final AutomationContext context;
    private static final String TYPE_ATTR = "type";
    private static final String VALUE_ATTR = "value";

    private static final String ERROR_MSG = "Error on parsing IVariable Type, content: [%s]";

    public ExpressionIVariableJsonAdapter(AutomationContext context) {
        this.context = context;
    }

    @Override
    public IVariable deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonElement messageType = getType(jsonElement);

        try {
            EVariableType blockType = EVariableType.valueOf(messageType.getAsString());
            if (EVariableType.TIME.equals(blockType)) {
                Map<String, String> expressions = getExpressionsAndRemoveItFromPrimitiveTypes((JsonObject) jsonElement);


                if (expressions.isEmpty()) {
                    return new TimeVariable(jsonElement.getAsJsonObject().get(VALUE_ATTR).getAsString());
                } else {
                    return new ExpressionProxy<>(TimeVariable.class, new TimeVariable(LocalTime.now()), expressions).build(context);
                }
            } else {
                return ExpressionProxyBuilder.instance(blockType.getClazz(), context).deserialize(jsonElement, jsonDeserializationContext);
            }
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
