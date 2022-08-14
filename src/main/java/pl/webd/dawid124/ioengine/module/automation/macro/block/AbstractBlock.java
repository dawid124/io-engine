package pl.webd.dawid124.ioengine.module.automation.macro.block;

import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.Map;

public abstract class AbstractBlock implements IBlock {

    private String name;
    private Integer order;
    private EBlockType blockType;

    @Override
    public abstract void run(Map<String, IVariable> variables, String zoneId);

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public EBlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(EBlockType blockType) {
        this.blockType = blockType;
    }

    public Integer getOrder() {
        if (order == null) return 0;
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
