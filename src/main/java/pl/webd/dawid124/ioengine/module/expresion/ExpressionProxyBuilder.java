package pl.webd.dawid124.ioengine.module.expresion;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;

import java.util.HashMap;
import java.util.Map;

public class ExpressionProxyBuilder<T> {
    private final Class<T> type;
    private final AutomationContext automationContext;

    private ExpressionProxyBuilder(Class<T> type, AutomationContext automationContext) {
        this.type = type;
        this.automationContext = automationContext;
    }

    public static <T> ExpressionProxyBuilder<T> instance(Class<T> type, AutomationContext automationContext) {
        return new ExpressionProxyBuilder<>(type, automationContext);
    }

    public T deserialize(JsonElement json, JsonDeserializationContext deserializationContext) {
        JsonObject originalJson = (JsonObject) json;

        Map<String, String> expressions = getExpressionsAndRemoveItFromPrimitiveTypes(originalJson);

        T obj = deserializationContext.deserialize(json, type);
        if (expressions.isEmpty()) {
            return obj;
        }

        return new ExpressionProxy<T>(type, obj, expressions).build(automationContext);
    }

    private Map<String, String> getExpressionsAndRemoveItFromPrimitiveTypes(JsonObject json) {

        Map<String, String> expressions = new HashMap<>();

        for (String key : json.keySet()) {
            JsonElement el = json.get(key);
            if (el.isJsonPrimitive() && el.getAsString().startsWith("$")) {
                expressions.put(key.toLowerCase(), el.getAsString());
            }
        }
        expressions.keySet().forEach(json::remove);

        return expressions;
    }
}
