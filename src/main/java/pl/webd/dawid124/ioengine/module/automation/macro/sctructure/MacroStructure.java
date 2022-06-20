package pl.webd.dawid124.ioengine.module.automation.macro.sctructure;

import java.util.HashMap;
import java.util.Map;

public class MacroStructure {

    private Map<String, Macro> macros;

    public MacroStructure() {
        macros = new HashMap<>();
    }

    public Map<String, Macro> getMacros() {
        return macros;
    }

    public void setMacros(Map<String, Macro> macros) {
        this.macros = macros;
    }
}
