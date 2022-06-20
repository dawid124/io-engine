package pl.webd.dawid124.ioengine.module.automation.trigger;

import pl.webd.dawid124.ioengine.module.automation.macro.block.IBlock;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.HashMap;
import java.util.List;

public class Trigger {

    private String zoneId;
    private List<IBlock> blocks;

    public void run(HashMap<String, IVariable> variables) {
        blocks.forEach(b -> b.run(variables, zoneId));
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public List<IBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<IBlock> blocks) {
        this.blocks = blocks;
    }
}
