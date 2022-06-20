package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.state.SystemArg;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.StringVariable;
import pl.webd.dawid124.ioengine.module.state.model.zone.ZoneState;
import pl.webd.dawid124.ioengine.module.state.service.StateService;
import pl.webd.dawid124.ioengine.module.automation.timer.TimerService;

import java.util.Map;

@Service
public class RunnerService {

    private StateService stateService;
    private TimerService timerService;

    public RunnerService(StateService stateService, TimerService timerService) {
        this.stateService = stateService;
        this.timerService = timerService;
    }

    void run(Map<String, IVariable> variables, String zoneId, RunnerBlock runnerBlock) {
        ZoneState zoneState = stateService.getZoneState().get(zoneId);
        switch (runnerBlock.getRunnerType()) {
            case LIGHT_ACTION:
                IVariable stateIdParam = variables.get(SystemArg.STATE_ID);
                String stateId;
                if (stateIdParam == null) {
                    stateId = zoneState.getActiveScene();
                } else {
                    stateId = ((StringVariable) stateIdParam).getValue();
                }
                SceneState sceneState = zoneState.getSceneStates().get(stateId);

                break;
            case TIMER:
                timerService.runTimer(variables, runnerBlock.getArgs());
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

    public void setTimerService(TimerService timerService) {
        this.timerService = timerService;
    }
}
