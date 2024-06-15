package pl.webd.dawid124.ioengine.module.automation.macro.json;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import pl.webd.dawid124.ioengine.module.action.model.rest.Color;
import pl.webd.dawid124.ioengine.module.action.model.rest.IColor;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.expresion.ExpressionProxyBuilder;

import java.lang.reflect.Type;

public class ExpressionColorJsonAdapter implements JsonDeserializer<IColor> {
    private final AutomationContext context;

    public ExpressionColorJsonAdapter(AutomationContext automationContext) {
        this.context = automationContext;
    }

    @Override
    public IColor deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
       return ExpressionProxyBuilder.instance(Color.class, context).deserialize(jsonElement, jsonDeserializationContext);
    }
}
