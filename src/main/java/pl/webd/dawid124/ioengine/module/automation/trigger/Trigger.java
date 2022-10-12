package pl.webd.dawid124.ioengine.module.automation.trigger;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.automation.macro.block.IBlock;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.HashMap;
import java.util.List;

public class Trigger {

    private String zoneId;
    private List<IBlock> blocs;

    public void run(AutomationContext context, HashMap<String, IVariable> variables) {
        blocs.forEach(b -> b.run(context, variables, zoneId));
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public List<IBlock> getBlocs() {
        return blocs;
    }

    public void setBlocs(List<IBlock> blocs) {
        this.blocs = blocs;
    }
}
