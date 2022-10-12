package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import org.springframework.util.CollectionUtils;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiActionRequest;
import pl.webd.dawid124.ioengine.module.action.model.server.LedChangeData;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.model.zone.ZoneState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LightStateActionRunnerBlock extends RunnerBlock {

    private final List<UiAction> actions;

    private final LedChangeData ledChangeData;

    private final List<String> sceneIds;

    public LightStateActionRunnerBlock(List<UiAction> actions, LedChangeData ledChangeData, List<String> sceneIds) {
        this.actions = actions;
        this.ledChangeData = ledChangeData;
        this.sceneIds = sceneIds;
    }

    @Override public ERunnerBlockType getRunnerType() {
        return ERunnerBlockType.STATE_ACTION;
    }

    @Override
    public void run(AutomationContext context, Map<String, IVariable> variables, String zoneId) {

        ZoneState zoneState = context.getStateService().getZoneState().get(zoneId);

        List<String> scenes = new ArrayList<>();
        if (CollectionUtils.isEmpty(sceneIds)) {
            scenes.add(zoneState.getActiveScene());
        } else {
            scenes.addAll(sceneIds);
        }

        scenes.forEach(id -> {
            SceneState sceneState = zoneState.getSceneStates().get(id);
            UiActionRequest action = new UiActionRequest(zoneId, sceneState.getId(), actions, ledChangeData);
            context.getActionService().processLights(action);
        });

    }

    public List<UiAction> getActions() {
        return actions;
    }

    public LedChangeData getLedChangeData() {
        return ledChangeData;
    }

    public List<String> getSceneIds() {
        return sceneIds;
    }
}
