package pl.webd.dawid124.ioengine.module.automatization.fetcher;

import org.springframework.stereotype.Component;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.StringVariable;
import pl.webd.dawid124.ioengine.module.state.service.StateService;

@Component
public class CurrentStateVariableFetcher implements IVariableFetcher {

    private StateService stateService;

    public CurrentStateVariableFetcher(StateService stateService) {
        this.stateService = stateService;
    }

    @Override
    public EVariableFetcherType getFetcherType() {
        return EVariableFetcherType.CURRENT_STATE_VARIABLE;
    }

    @Override
    public IVariable fetch(String zoneId) {
        return new StringVariable(stateService.getZoneState().get(zoneId).getActiveScene());
    }
}
