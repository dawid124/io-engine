package pl.webd.dawid124.ioengine.module.automation.macro.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.springframework.stereotype.Component;
import pl.webd.dawid124.ioengine.module.automation.macro.fetcher.CurrentStateVariableFetcher;
import pl.webd.dawid124.ioengine.module.automation.macro.fetcher.EVariableFetcherType;
import pl.webd.dawid124.ioengine.module.automation.macro.fetcher.IVariableFetcher;
import pl.webd.dawid124.ioengine.module.automation.macro.fetcher.MacroVariableFetcher;
import pl.webd.dawid124.ioengine.module.state.SystemArg;

import java.lang.reflect.Type;

@Component
public class VariableFetcherJsonAdapter implements JsonDeserializer<IVariableFetcher> {

    private static final String TYPE_ATTR = "fetcherType";
    private static final String ERROR_MSG = "Error on parsing IBlock Type, type: [%s]";

    private final CurrentStateVariableFetcher currentStateVariableFetcher;

    public VariableFetcherJsonAdapter(CurrentStateVariableFetcher currentStateVariableFetcher) {
        this.currentStateVariableFetcher = currentStateVariableFetcher;
    }

    @Override
    public IVariableFetcher deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonElement messageType = getType(jsonElement);

        try {
            EVariableFetcherType blockType = EVariableFetcherType.valueOf(messageType.getAsString());
            switch (blockType) {
                case MACRO_VARIABLE:
                    return new MacroVariableFetcher(jsonElement.getAsJsonObject().get(SystemArg.KEY).getAsString());
                case CURRENT_STATE_VARIABLE:
                    return currentStateVariableFetcher;
            }
        } catch (Exception e) {
            throw new JsonParseException(String.format(ERROR_MSG, jsonElement), e);
        }

        return null;
    }

    private JsonElement getType(JsonElement jsonElement) {
        JsonElement messageType = jsonElement.getAsJsonObject().get(TYPE_ATTR);

        if (messageType == null || messageType.getAsString().length() == 0) {
            throw new JsonParseException(String.format(ERROR_MSG, jsonElement));
        }

        return messageType;
    }
}
