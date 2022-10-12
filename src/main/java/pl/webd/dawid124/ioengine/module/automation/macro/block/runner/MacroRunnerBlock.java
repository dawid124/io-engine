package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.HashMap;
import java.util.Map;

public class MacroRunnerBlock extends RunnerBlock {

    private final String macroId;

    private final Map<String, IVariable> variables;

    public MacroRunnerBlock(String macroId) {
        this.macroId = macroId;
        variables = new HashMap<>();
    }

    public MacroRunnerBlock(String macroId, Map<String, IVariable> variables) {
        this.macroId = macroId;
        this.variables = variables;
    }

    @Override public ERunnerBlockType getRunnerType() {
        return ERunnerBlockType.MACRO_RUNNER;
    }

    @Override
    public void run(AutomationContext context, Map<String, IVariable> parentVariables, String zoneId) {
        HashMap<String, IVariable> newMap = new HashMap<>();
        if (parentVariables != null) newMap.putAll(parentVariables);
        if (this.variables != null) newMap.putAll(this.variables);

        context.getMacroService().runMacro(newMap, this.macroId);
    }

    public String getMacroId() {
        return macroId;
    }

    public Map<String, IVariable> getVariables() {
        return variables;
    }
}
