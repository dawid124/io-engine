package pl.webd.dawid124.ioengine.module.automation.macro.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import pl.webd.dawid124.ioengine.module.automation.macro.fetcher.EVariableFetcherType;
import pl.webd.dawid124.ioengine.module.automation.macro.fetcher.IVariableFetcher;

import java.lang.reflect.Type;

public class IVariableFetcherJsonAdapter implements JsonDeserializer<IVariableFetcher> {

    private static final String TYPE_ATTR = "fetcherType";
    private static final String ERROR_MSG = "Error on parsing IBlock Type, type: [%s]";


    @Override
    public IVariableFetcher deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonElement messageType = getType(jsonElement);

        try {
            EVariableFetcherType blockType = EVariableFetcherType.valueOf(messageType.getAsString());
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
