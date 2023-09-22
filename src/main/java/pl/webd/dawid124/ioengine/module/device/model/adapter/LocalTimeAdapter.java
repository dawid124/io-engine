package pl.webd.dawid124.ioengine.module.device.model.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalTime;

public class LocalTimeAdapter implements JsonDeserializer<LocalTime> {
    @Override
    public LocalTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        String str = jsonElement.getAsString();

        String[] numbers = str.split(":");
        int hour = Integer.parseInt(numbers[0]);
        int minutes = Integer.parseInt(numbers[1]);
        return LocalTime.of(hour, minutes);
    }
}
