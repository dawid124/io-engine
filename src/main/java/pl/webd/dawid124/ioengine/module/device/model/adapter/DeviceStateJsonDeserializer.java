package pl.webd.dawid124.ioengine.module.device.model.adapter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.gson.*;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.EDeviceStateType;
import pl.webd.dawid124.ioengine.mqtt.action.IoAction;
import pl.webd.dawid124.ioengine.mqtt.action.adapter.IoActionJsonPartyAdapter;

import java.io.IOException;
import java.lang.reflect.Type;

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
