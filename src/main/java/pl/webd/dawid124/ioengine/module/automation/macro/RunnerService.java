package pl.webd.dawid124.ioengine.module.automation.macro;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiActionRequest;
import pl.webd.dawid124.ioengine.module.action.service.UserActionService;
import pl.webd.dawid124.ioengine.module.automation.macro.block.runner.LightActionRunnerBlock;
import pl.webd.dawid124.ioengine.module.automation.macro.block.runner.MacroRunnerBlock;
import pl.webd.dawid124.ioengine.module.automation.macro.block.runner.RunnerBlock;
import pl.webd.dawid124.ioengine.module.automation.macro.block.runner.TimerRunnerBlock;
import pl.webd.dawid124.ioengine.module.state.SystemArg;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.StringVariable;
import pl.webd.dawid124.ioengine.module.state.model.zone.ZoneState;
import pl.webd.dawid124.ioengine.module.state.service.StateService;
import pl.webd.dawid124.ioengine.module.automation.timer.TimerService;

import java.util.HashMap;
import java.util.Map;

@Service
public class RunnerService {

    private final StateService stateService;
    private final TimerService timerService;
    private final MacroService macroService;
    private final UserActionService userActionService;

    public RunnerService(StateService stateService, TimerService timerService, MacroService macroService, UserActionService userActionService) {
        this.stateService = stateService;
        this.timerService = timerService;
        this.macroService = macroService;
        this.userActionService = userActionService;
    }

    public boolean run(RunnerBlock runner, Map<String, IVariable> variables, String zoneId) {
        ZoneState zoneState = stateService.getZoneState().get(zoneId);
        switch (runner.getRunnerType()) {
            case LIGHT_ACTION:
                return processAction((LightActionRunnerBlock) runner, variables, zoneId, zoneState);
            case TIMER:
                return processTimer((TimerRunnerBlock) runner, variables);
            case MACRO_RUNNER:
                return processMacro((MacroRunnerBlock) runner, variables);
            case BLIND_ACTION:

                break;
            case GLOBAL_VARIABLE_CHANGE:

                break;
            case ZONE_VARIABLE_CHANGE:

                break;
            case LOG:

                break;
        }

        return false;
    }

    private boolean processMacro(MacroRunnerBlock runner, Map<String, IVariable> variables) {
        HashMap<String, IVariable> newMap = new HashMap<>();
        newMap.putAll(variables);
        if (runner.getVariables() != null) newMap.putAll(runner.getVariables());
        macroService.runMacro(newMap, runner.getMacroId());

        return true;
    }

    private boolean processTimer(TimerRunnerBlock runner, Map<String, IVariable> variables) {
        timerService.runTimer(variables, runner);

        return true;
    }

    private boolean processAction(LightActionRunnerBlock runner, Map<String, IVariable> variables, String zoneId, ZoneState zoneState) {
        String stateId = getStateId(variables, zoneState);
        SceneState sceneState = zoneState.getSceneStates().get(stateId);

        userActionService.processActionChange(new UiActionRequest(zoneId, sceneState.getId(), runner.getActions(), runner.getLedChangeData()));

        return true;
    }

    private String getStateId(Map<String, IVariable> variables, ZoneState zoneState) {
        String stateId;
        IVariable stateIdParam = variables.get(SystemArg.STATE_ID);
        if (stateIdParam == null) stateId = zoneState.getActiveScene();
        else stateId = ((StringVariable) stateIdParam).getValue();
        return stateId;
    }
}
