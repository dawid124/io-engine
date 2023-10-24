package pl.webd.dawid124.ioengine.module.automation.macro.fetcher;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.Map;

public class GlobalVariableFetcher implements IVariableFetcher {

    private String key;

    public GlobalVariableFetcher(String key) {
        this.key = key;
    }

    @Override
    public EVariableFetcherType getFetcherType() {
        return EVariableFetcherType.GLOBAL_VARIABLE;
    }

    @Override
    public IVariable fetch(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        return context.getStateService().getVariables().get(key).getVar();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
