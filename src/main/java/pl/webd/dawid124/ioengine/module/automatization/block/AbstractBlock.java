package pl.webd.dawid124.ioengine.module.automatization.block;

import java.util.Map;

public abstract class AbstractBlock implements IBlock {

    private String name;
    private Integer order;
    private EBlockType blockType;

    @Override
    public abstract void run(Map<String, Object> variables, String zoneId);

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
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}