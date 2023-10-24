package pl.webd.dawid124.ioengine.module.automation.macro.fetcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.webd.dawid124.ioengine.module.action.service.ActionService;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.variable.*;
import pl.webd.dawid124.ioengine.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;

public class DeviceStateFetcher implements IVariableFetcher {

    private static final Logger LOG = LogManager.getLogger(DeviceStateFetcher.class);
    private String ioId;
    private String key;

    @Override
    public EVariableFetcherType getFetcherType() {
        return EVariableFetcherType.DEVICE_STATE;
    }

    @Override
    public IVariable fetch(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        DeviceState state = context.getStateService().getSensors().get(ioId);

        try {
            Field field = ReflectionUtils.findUnderlying(state.getClass(), key);
            field.setAccessible(true);
            switch (field.getType().getName()) {
                case "java.lang.String":
                    return new StringVariable((String) field.get(state));
                case "boolean":
                    return new BooleanVariable(field.getBoolean(state));
                case "integer":
                    return new IntegerVariable(field.getInt(state));
                case "double":
                    return new DoubleVariable(field.getFloat(state));
            }
        } catch (Exception e) {
            LOG.warn("Device state fetcher error. ioId: [{}], key: [{}]", ioId, key, e);
        }

        return null;
    }

    public String getIoId() {
        return ioId;
    }

    public void setIoId(String ioId) {
        this.ioId = ioId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
