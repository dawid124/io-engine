package pl.webd.dawid124.ioengine.module.automation.macro.block;

import pl.webd.dawid124.ioengine.module.automation.macro.block.collection.CollectionBlock;
import pl.webd.dawid124.ioengine.module.automation.macro.block.runner.RunnerBlock;

public enum EBlockType {

    BLOCK(CollectionBlock.class),
    CONDITION(ConditionBlock.class),
    RUNNER(RunnerBlock.class);

    Class clazz;
    EBlockType(Class clazz) {
        this.clazz = clazz;
    }
    public Class getClazz() {
        return clazz;
    }
}
