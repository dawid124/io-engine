package pl.webd.dawid124.ioengine.module.automation.macro.fetcher;

import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.Map;

public class MacroVariableFetcher implements IVariableFetcher {

    private String key;

    public MacroVariableFetcher(String key) {
        this.key = key;
    }

    @Override
    public EVariableFetcherType getFetcherType() {
        return EVariableFetcherType.MACRO_VARIABLE;
    }

    @Override
    public IVariable fetch(Map<String, IVariable> variables, String zoneId) {
        return variables.get(key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
