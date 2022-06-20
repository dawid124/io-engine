package pl.webd.dawid124.ioengine.module.automation.macro.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.springframework.stereotype.Component;
import pl.webd.dawid124.ioengine.module.automation.macro.block.EBlockType;
import pl.webd.dawid124.ioengine.module.automation.macro.block.IBlock;
import pl.webd.dawid124.ioengine.module.automation.macro.block.runner.ERunnerBlockType;
import pl.webd.dawid124.ioengine.module.automation.macro.block.runner.RunnerBlock;
import pl.webd.dawid124.ioengine.module.automation.macro.RunnerService;

import java.lang.reflect.Type;

@Component
public class IBlockJsonAdapter implements JsonDeserializer<IBlock> {

    private RunnerService runnerService;

    private static final String TYPE_ATTR = "blockType";
    private static final String RUNNER_TYPE_ATTR = "runnerType";

    private static final String ERROR_MSG = "Error on parsing IBlock Type, content: [%s]";

    public IBlockJsonAdapter(RunnerService runnerService) {
        this.runnerService = runnerService;
    }

    @Override
    public IBlock deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonElement messageType = getType(jsonElement);

        try {
            EBlockType blockType = EBlockType.valueOf(messageType.getAsString());
            if (EBlockType.RUNNER.equals(blockType)) {
                return deserializeRunner(jsonElement, context);
            } else {
                return context.deserialize(jsonElement, blockType.getClazz());
            }

        } catch (Exception e) {
            throw new JsonParseException(String.format(ERROR_MSG, jsonElement), e);
        }
    }

    private RunnerBlock deserializeRunner(JsonElement jsonElement, JsonDeserializationContext context) {
        ERunnerBlockType runnerType = ERunnerBlockType.valueOf(getRunnerType(jsonElement).getAsString());
        RunnerBlock deserialize = context.deserialize(jsonElement, runnerType.getClazz());
        deserialize.setRunnerService(runnerService);

        return deserialize;
    }

    private JsonElement getType(JsonElement jsonElement) {
        JsonElement messageType = jsonElement.getAsJsonObject().get(TYPE_ATTR);

        if (messageType == null || messageType.getAsString().length() == 0) {
            throw new JsonParseException(String.format(ERROR_MSG, jsonElement));
        }

        return messageType;
    }

    private JsonElement getRunnerType(JsonElement jsonElement) {
        JsonElement messageType = jsonElement.getAsJsonObject().get(RUNNER_TYPE_ATTR);

        if (messageType == null || messageType.getAsString().length() == 0) {
            throw new JsonParseException(String.format(ERROR_MSG, jsonElement));
        }

        return messageType;
    }
}
