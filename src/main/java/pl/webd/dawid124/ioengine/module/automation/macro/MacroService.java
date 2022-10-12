package pl.webd.dawid124.ioengine.module.automation.macro;

import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.automation.macro.sctructure.MacroStructure;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.Map;

@Service public class MacroService {

    private final AutomationContext context;

    private MacroStructure macroHome;

    public MacroService(AutomationContext context) {
        this.context = context;
        this.macroHome = new MacroStructure();
    }

    public void runMacro(Map<String, IVariable> variables, String id) {
        macroHome.getMacros().get(id).run(context, variables);
    }

    public MacroStructure getMacroHome() {
        return macroHome;
    }

    public void setMacroHome(MacroStructure macroHome) {
        this.macroHome = macroHome;
    }
}
