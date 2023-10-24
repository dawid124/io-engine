package pl.webd.dawid124.ioengine.module.device.model.adapter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;

import java.io.IOException;

public class DeviceStateJsonDeserializer extends StdDeserializer<DeviceState> {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(DeviceState.class, new DeviceStateJsonAdapter())
            .create();

    public DeviceStateJsonDeserializer() {
        super(DeviceState.class);
    }

    protected DeviceStateJsonDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public DeviceState deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        String tree = jp.readValueAsTree().toString();
        return (DeviceState) gson.fromJson(tree, _valueClass);
    }
}
