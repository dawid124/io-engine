package pl.webd.dawid124.ioengine.module.state.model.variable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.jsondb.annotation.Document;
import pl.webd.dawid124.ioengine.module.automation.macro.exception.NotSupportedConditionException;
import pl.webd.dawid124.ioengine.module.automation.macro.json.IVariableJsonDeserializer;
import pl.webd.dawid124.ioengine.module.device.model.adapter.DeviceStateJsonDeserializer;

import java.util.List;

@JsonDeserialize(using = IVariableJsonDeserializer.class)
public interface IVariable {

    EVariableType getType();

    Object getValue();

    boolean equals(IVariable test);

    default boolean contains(IVariable test) {
        throw new NotSupportedConditionException();
    }

    default boolean notEquals(IVariable test) {
        return !equals(test);
    }

    default boolean lt(IVariable test) {
        throw new NotSupportedConditionException();
    }

    default boolean gt(IVariable test) {
        throw new NotSupportedConditionException();
    }

    default boolean le(IVariable test) {
        throw new NotSupportedConditionException();
    }

    default boolean ge(IVariable test) {
        throw new NotSupportedConditionException();
    }

    default boolean in(List<IVariable> test) {
        throw new NotSupportedConditionException();
    }
}
