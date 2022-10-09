package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

public enum ERunnerBlockType {
    LOG(null),
    MACRO_RUNNER(MacroRunnerBlock.class),
    LIGHT_ACTION(LightActionRunnerBlock.class),
    ACTION(ActionRunnerBlock.class),
    CMD(CMDRunnerBlock.class),
    SCENE_CHANGE(SceneChangeRunnerBlock.class),
    BLIND_ACTION(null),
    GLOBAL_VARIABLE_CHANGE(null),
    ZONE_VARIABLE_CHANGE(null),
    TIMER(TimerRunnerBlock.class);

    Class clazz;
    ERunnerBlockType(Class clazz) {
        this.clazz = clazz;
    }
    public Class getClazz() {
        return clazz;
    }
}
