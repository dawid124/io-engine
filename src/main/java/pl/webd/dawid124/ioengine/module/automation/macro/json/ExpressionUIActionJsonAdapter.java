package pl.webd.dawid124.ioengine.module.automation.macro.json;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import pl.webd.dawid124.ioengine.module.action.model.rest.IUiAction;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.expresion.ExpressionProxyBuilder;

import java.lang.reflect.Type;

public class ExpressionUIActionJsonAdapter implements JsonDeserializer<IUiAction> {
    private final AutomationContext context;

    public ExpressionUIActionJsonAdapter(AutomationContext automationContext) {
        this.context = automationContext;
    }

    @Override
    public IUiAction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {

       return ExpressionProxyBuilder.instance(UiAction.class, context).deserialize(jsonElement, jsonDeserializationContext);
    }
}
