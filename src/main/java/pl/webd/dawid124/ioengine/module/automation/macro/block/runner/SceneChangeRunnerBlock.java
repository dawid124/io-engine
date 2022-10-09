package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import pl.webd.dawid124.ioengine.module.automation.macro.RunnerService;

public class SceneChangeRunnerBlock extends RunnerBlock {

    private final String newSceneId;

    public SceneChangeRunnerBlock(RunnerService runnerService, String newSceneId) {
        super(runnerService);
        this.newSceneId = newSceneId;
    }

    @Override public ERunnerBlockType getRunnerType() {
        return ERunnerBlockType.SCENE_CHANGE;
    }

    public String getNewSceneId() {
        return newSceneId;
    }
}
