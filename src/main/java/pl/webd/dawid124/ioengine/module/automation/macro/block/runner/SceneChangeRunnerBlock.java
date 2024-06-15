package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import org.springframework.util.StringUtils;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.Map;

public class SceneChangeRunnerBlock extends RunnerBlock {

    private final String newSceneId;
    private final String zoneId;

    public SceneChangeRunnerBlock(String newSceneId) {
        this.newSceneId = newSceneId;
        this.zoneId = null;
    }

    public SceneChangeRunnerBlock(String newSceneId, String zoneId) {
        this.newSceneId = newSceneId;
        this.zoneId = zoneId;
    }

    @Override public ERunnerBlockType getRunnerType() {
        return ERunnerBlockType.SCENE_CHANGE;
    }

    @Override
    public void run(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        if (StringUtils.hasText(this.zoneId)) {
            context.getActionService().processSceneChange(this.zoneId, newSceneId);
        } else {
            context.getActionService().processSceneChange(zoneId, newSceneId);
        }

    }

    public String getNewSceneId() {
        return newSceneId;
    }
}
