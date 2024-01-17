package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import pl.webd.dawid124.ioengine.module.action.model.rest.EActionType;
import pl.webd.dawid124.ioengine.module.action.model.rest.IUiAction;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiActionRequest;
import pl.webd.dawid124.ioengine.module.action.model.server.LedChangeData;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.device.model.output.EDeviceType;
import pl.webd.dawid124.ioengine.module.state.model.device.GroupState;
import pl.webd.dawid124.ioengine.module.state.model.device.LedDeviceState;
import pl.webd.dawid124.ioengine.module.state.model.variable.DoubleVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.StringVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SceneBrightnessChangeRunnerBlock extends RunnerBlock {

    private final Integer value;

    public SceneBrightnessChangeRunnerBlock(Integer value) {
        this.value = value;
    }

    @Override public ERunnerBlockType getRunnerType() {
        return ERunnerBlockType.SCENE_CHANGE;
    }

    @Override
    public void run(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        int stepSize = ((DoubleVariable) variables.get("action_step_size")).getValue().intValue();
        String action = ((StringVariable) variables.get("action")).getValue();

        String activeScene = context.getStateService().getZoneState().get(zoneId).getActiveScene();
        ArrayList<IUiAction> ioActions = new ArrayList<>();

        GroupState groupState = context.getStateService().getZoneState().get(zoneId).getSceneStates().get(activeScene)
                .getGroupState().values().stream().findFirst().get();

        UiAction a = new UiAction();
        a.setIoId(groupState.getState().getIoId());
        a.setIoType(EDeviceType.LED);
        a.setAction(EActionType.CHANGE);

        int currentBrightness = ((LedDeviceState) groupState.getState()).getBrightness();
        if ("brightness_step_up".equals(action)) {
            a.setBrightness(Math.min(255, currentBrightness + stepSize));
        } else if ("brightness_step_down".equals(action)) {
            a.setBrightness(Math.max(0, currentBrightness - stepSize));
        }


        ioActions.add(a);
        UiActionRequest request = new UiActionRequest(zoneId, activeScene, ioActions, new LedChangeData());
        context.getActionService().processActionChange(request);
    }

    public Integer getValue() {
        return value;
    }
}
