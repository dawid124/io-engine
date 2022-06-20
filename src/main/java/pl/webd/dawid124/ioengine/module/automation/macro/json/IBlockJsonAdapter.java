package pl.webd.dawid124.ioengine.module.automation.macro.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.springframework.stereotype.Component;
import pl.webd.dawid124.ioengine.module.automation.macro.block.EBlockType;
import pl.webd.dawid124.ioengine.module.automation.macro.block.IBlock;
import pl.webd.dawid124.ioengine.module.automation.macro.block.runner.RunnerBlock;
import pl.webd.dawid124.ioengine.module.automation.macro.block.runner.RunnerService;

import java.lang.reflect.Type;

@Component
public class IBlockJsonAdapter implements JsonDeserializer<IBlock> {

    private RunnerService runnerService;

    private static final String TYPE_ATTR = "blockType";

    private static final String ERROR_MSG = "Error on parsing IBlock Type, content: [%s]";

    public IBlockJsonAdapter(RunnerService runnerService) {
        this.runnerService = runnerService;
    }

    @Override
    public IBlock deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonElement messageType = getType(jsonElement);

        try {
            EBlockType blockType = EBlockType.valueOf(messageType.getAsString());
            IBlock deserialize = context.deserialize(jsonElement, blockType.getClazz());
            if (deserialize instanceof RunnerBlock) {
                ((RunnerBlock) deserialize).setRunnerService(runnerService);
            }
            return deserialize;
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
