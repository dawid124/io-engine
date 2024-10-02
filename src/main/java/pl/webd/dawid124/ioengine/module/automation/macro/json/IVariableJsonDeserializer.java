package pl.webd.dawid124.ioengine.module.automation.macro.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.io.IOException;

public class IVariableJsonDeserializer extends StdDeserializer<IVariable> {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(IVariable.class, new IVariableJsonAdapter())
            .create();

    public IVariableJsonDeserializer() {
        super(IVariable.class);
    }

    protected IVariableJsonDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public IVariable deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        String tree = jp.readValueAsTree().toString();
        return (IVariable) gson.fromJson(tree, _valueClass);
    }
}
