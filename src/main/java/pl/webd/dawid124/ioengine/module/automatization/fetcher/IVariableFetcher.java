package pl.webd.dawid124.ioengine.module.automatization.fetcher;

import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

public interface IVariableFetcher {

    EVariableFetcherType getFetcherType();

    IVariable fetch(String zoneId);
}
