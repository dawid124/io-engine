package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import pl.webd.dawid124.ioengine.module.automation.macro.RunnerService;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.HashMap;
import java.util.Map;

public class MacroRunnerBlock extends RunnerBlock {

    private final String macroId;

    private final Map<String, IVariable> variables;

    public MacroRunnerBlock(String macroId, RunnerService runnerService) {
        super(runnerService);
        this.macroId = macroId;
        variables = new HashMap<>();
    }

    public MacroRunnerBlock(RunnerService runnerService, String macroId, Map<String, IVariable> variables) {
        super(runnerService);
        this.macroId = macroId;
        this.variables = variables;
    }

    @Override public ERunnerBlockType getRunnerType() {
        return ERunnerBlockType.MACRO_RUNNER;
    }

    public String getMacroId() {
        return macroId;
    }

    public Map<String, IVariable> getVariables() {
        return variables;
    }
}
