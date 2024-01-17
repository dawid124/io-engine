package pl.webd.dawid124.ioengine.module.structure.model;

import pl.webd.dawid124.ioengine.module.automation.macro.block.condition.ICondition;

public class MacroButton extends Macro {
    private String unClickMacroId;

    private ICondition activeCondition;
    private boolean active;

    public String getUnClickMacroId() {
        return unClickMacroId;
    }

    public void setUnClickMacroId(String unClickMacroId) {
        this.unClickMacroId = unClickMacroId;
    }

    public ICondition getActiveCondition() {
        return activeCondition;
    }

    public void setActiveCondition(ICondition activeCondition) {
        this.activeCondition = activeCondition;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
