package pl.webd.dawid124.ioengine.module.state.model.variable;

import com.google.gson.JsonElement;

public class VariableFactory {

    public static IVariable getVariable(JsonElement value) {
        try {
            if (value.isJsonPrimitive()) {
                if (value.getAsJsonPrimitive().isBoolean())
                    return new BooleanVariable(value.getAsBoolean());
                if (value.getAsJsonPrimitive().isString())
                    return new StringVariable(value.getAsString());
                if (value.getAsJsonPrimitive().isNumber()){
                    return new DoubleVariable(value.getAsNumber().doubleValue());
                }
            }
        } catch (Exception ex) {
            return new StringVariable("");
        }
        return new StringVariable("");
    }
}
