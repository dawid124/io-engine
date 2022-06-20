package pl.webd.dawid124.ioengine.module.automatization.block.runner;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.state.SystemVar;

import java.util.Map;

@Service
public class RunnerService {

    void run(Map<String, Object> variables, String zoneId, RunnerBlock runnerBlock) {
        switch (runnerBlock.getRunnerType()) {
            case LIGHT_ACTION:
                String stateId = (String) variables.get(SystemVar.STATE_ID);

                break;
            case BLIND_ACTION:

                break;
            case GLOBAL_VARIABLE_CHANGE:

                break;
            case ZONE_VARIABLE_CHANGE:

                break;
            case MACRO_RUNNER:

                break;
            case LOG:

                break;
        }
    }
}
