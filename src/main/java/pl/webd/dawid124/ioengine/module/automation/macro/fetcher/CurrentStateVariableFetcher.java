package pl.webd.dawid124.ioengine.module.automation.macro.fetcher;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.StringVariable;

import java.util.Map;

public class CurrentStateVariableFetcher implements IVariableFetcher {

    @Override
    public EVariableFetcherType getFetcherType() {
        return EVariableFetcherType.CURRENT_STATE_VARIABLE;
    }

    @Override
    public IVariable fetch(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        return new StringVariable(context.getStateService().getZoneState().get(zoneId).getActiveScene());
    }
}
