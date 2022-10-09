package pl.webd.dawid124.ioengine.module.automation.macro;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiActionRequest;
import pl.webd.dawid124.ioengine.module.action.service.UserActionService;
import pl.webd.dawid124.ioengine.module.automation.macro.block.runner.*;
import pl.webd.dawid124.ioengine.module.automation.timer.TimerService;
import pl.webd.dawid124.ioengine.module.state.SystemArg;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.StringVariable;
import pl.webd.dawid124.ioengine.module.state.model.zone.ZoneState;
import pl.webd.dawid124.ioengine.module.state.service.StateService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class RunnerService {

    private static final Logger LOG = LogManager.getLogger( RunnerService.class );

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
        switch (runner.getRunnerType()) {
            case LIGHT_ACTION:
                ZoneState zoneState = stateService.getZoneState().get(zoneId);
                return processLitghtAction((LightActionRunnerBlock) runner, variables, zoneId, zoneState);
            case TIMER:
                return processTimer((TimerRunnerBlock) runner, variables);
            case MACRO_RUNNER:
                return processMacro((MacroRunnerBlock) runner, variables);
            case CMD:
                return processCmd((CMDRunnerBlock) runner, variables);
            case ACTION:
                return processAction((ActionRunnerBlock) runner);
            case SCENE_CHANGE:
                return processSceneChange((SceneChangeRunnerBlock) runner, zoneId);
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


    public static boolean executeBashCommand(String command) {
        boolean success = false;
//        LOG.warn("Executing BASH command:\n   " + command);
        Runtime r = Runtime.getRuntime();
        String[] commands = {"bash", "-c", command};
        try {
            Process p = r.exec(commands);

            int i = p.waitFor();
            BufferedReader b = null;
            if (i == 0) {
                b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            } else {
                b = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            }
            String line;

//            while ((line = b.readLine()) != null) {
//                LOG.warn(line);
//            }

            b.close();
            success = true;
        } catch (Exception e) {
            LOG.error("Failed to execute bash with command: " + command);
            e.printStackTrace();
        }
        return success;
    }

    private boolean processCmd(CMDRunnerBlock runner, Map<String, IVariable> variables) {
        executeBashCommand(runner.getCmd());

        return true;
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

    private boolean processLitghtAction(LightActionRunnerBlock runner, Map<String, IVariable> variables, String zoneId, ZoneState zoneState) {
        String stateId = getStateId(variables, zoneState);
        SceneState sceneState = zoneState.getSceneStates().get(stateId);

        userActionService.processActionChange(new UiActionRequest(zoneId, sceneState.getId(), runner.getActions(), runner.getLedChangeData()));

        return true;
    }

    private boolean processSceneChange(SceneChangeRunnerBlock runner, String zoneId) {
        userActionService.processSceneChange(zoneId, runner.getNewSceneId());
        return true;
    }

    private boolean processAction(ActionRunnerBlock runner) {
        userActionService.processSimpleActions(runner.getActions());

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
