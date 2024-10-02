package pl.webd.dawid124.ioengine.module.automation.macro.fetcher;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.IntegerVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.StringVariable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class CurrentMonthFetcher implements IVariableFetcher {

    @Override
    public EVariableFetcherType getFetcherType() {
        return EVariableFetcherType.CURRENT_MONTH;
    }

    @Override
    public IVariable fetch(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        return new IntegerVariable(LocalDate.now().getMonthValue());
    }
}
