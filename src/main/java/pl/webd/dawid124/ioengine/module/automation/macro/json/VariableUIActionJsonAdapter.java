package pl.webd.dawid124.ioengine.module.automation.macro.json;


import com.google.gson.*;
import pl.webd.dawid124.ioengine.module.action.model.rest.IUiAction;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;

import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VariableUIActionJsonAdapter implements JsonDeserializer<IUiAction> {

    public static final Gson GSON = (new Gson());
    private final AutomationContext context;

    public VariableUIActionJsonAdapter(AutomationContext automationContext) {
        this.context = automationContext;
    }

    @Override
    public IUiAction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject originalJson = (JsonObject) jsonElement;
        Set<String> keySet = originalJson.keySet();

        JsonObject newJson = new JsonObject();
        Map<String, String> expressions = new HashMap<>();
        for (String key : keySet) {
            JsonElement el = originalJson.get(key);
            if (el.isJsonPrimitive() && el.getAsString().startsWith("$")) {
                expressions.put(key.toLowerCase(), el.getAsString());
            } else {
                newJson.add(key, el);
            }
        }

        if (expressions.isEmpty()) {
            return GSON.fromJson(jsonElement.toString(), UiAction.class);
        }

        UiAction uiAction = GSON.fromJson(newJson.toString(), UiAction.class);
        return (IUiAction) Proxy.newProxyInstance(IUiAction.class.getClassLoader(), new Class[]{IUiAction.class},
                (proxy, method, methodArgs) -> {

                    String filed = method.getName();
                    if (method.getName().startsWith("get")) {
                        filed = filed.substring(3);
                    } else if (method.getName().startsWith("is")) {
                        filed = filed.substring(2);
                    }

                    String expression = expressions.get(filed.toLowerCase());
                    if (expression != null) {
                        String variable = expression.substring(2, expression.length() - 1);
                        return context.getStateService().getVariables().get(variable).getValue();
                    } else {
                        return uiAction.getClass().getDeclaredMethod(method.getName()).invoke(uiAction, methodArgs);
                    }
                });
    }
}
