package pl.webd.dawid124.ioengine.module.automation.macro.fetcher;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.Map;

public interface IVariableFetcher {

    EVariableFetcherType getFetcherType();

    IVariable fetch(AutomationContext context, Map<String, IVariable> variables, String zoneId);
}
