package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.Map;

public class SceneChangeRunnerBlock extends RunnerBlock {

    private final String newSceneId;

    public SceneChangeRunnerBlock(String newSceneId) {
        this.newSceneId = newSceneId;
    }

    @Override public ERunnerBlockType getRunnerType() {
        return ERunnerBlockType.SCENE_CHANGE;
    }

    @Override
    public void run(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        context.getActionService().processSceneChange(zoneId, newSceneId);
    }

    public String getNewSceneId() {
        return newSceneId;
    }
}
