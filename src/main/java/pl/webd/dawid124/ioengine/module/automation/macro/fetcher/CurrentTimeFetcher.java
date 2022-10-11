package pl.webd.dawid124.ioengine.module.automation.macro.fetcher;

import org.springframework.stereotype.Component;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.TimeVariable;

import java.time.LocalTime;
import java.util.Map;

@Component
public class CurrentTimeFetcher implements IVariableFetcher {


    @Override
    public EVariableFetcherType getFetcherType() {
        return EVariableFetcherType.CURRENT_TIME;
    }

    @Override
    public IVariable fetch(Map<String, IVariable> variables, String zoneId) {
        return new TimeVariable(LocalTime.now());
    }
}
