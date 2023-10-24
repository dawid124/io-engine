package pl.webd.dawid124.ioengine.module.structure.model;

public class Macro {

    private String id;
    private String label;
    private String clickMacroId;
    private String holdClickMacroId;

    public Macro() {}

    public Macro(String id, String label, String clickMacroId, String holdClickMacroId) {
        this.id = id;
        this.label = label;
        this.clickMacroId = clickMacroId;
        this.holdClickMacroId = holdClickMacroId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getClickMacroId() {
        return clickMacroId;
    }

    public void setClickMacroId(String clickMacroId) {
        this.clickMacroId = clickMacroId;
    }

    public String getHoldClickMacroId() {
        return holdClickMacroId;
    }

    public void setHoldClickMacroId(String holdClickMacroId) {
        this.holdClickMacroId = holdClickMacroId;
    }
}
