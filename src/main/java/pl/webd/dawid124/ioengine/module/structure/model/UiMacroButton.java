package pl.webd.dawid124.ioengine.module.structure.model;

public class UiMacroButton extends Macro {
    private String unClickMacroId;

    private boolean active;

    public UiMacroButton() {}

    public UiMacroButton(String unClickMacroId, boolean active) {
        this.unClickMacroId = unClickMacroId;
        this.active = active;
    }

    public UiMacroButton(MacroButton macroButton, boolean active) {
        super(macroButton.getId(), macroButton.getLabel(), macroButton.getClickMacroId(), macroButton.getHoldClickMacroId());
        this.unClickMacroId = macroButton.getUnClickMacroId();
        this.active = active;
    }

    public String getUnClickMacroId() {
        return unClickMacroId;
    }

    public void setUnClickMacroId(String unClickMacroId) {
        this.unClickMacroId = unClickMacroId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
