package pl.webd.dawid124.ioengine.module.action.model.server;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonDeserialize(as = LedChangeData.class)
@JsonSerialize(as = LedChangeData.class)
public interface ILedChangeData {
    int getTime();

    int getStepTime();
}
