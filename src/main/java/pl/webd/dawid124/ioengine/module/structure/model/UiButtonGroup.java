package pl.webd.dawid124.ioengine.module.structure.model;

import java.util.List;

public class UiButtonGroup {

    private String id;
    private String label;
    private List<UiMacroButton> buttons;

    public UiButtonGroup() {}

    public UiButtonGroup(ButtonGroup buttonGroup, List<UiMacroButton> buttons) {
        this.id = buttonGroup.getId();
        this.label = buttonGroup.getLabel();
        this.buttons = buttons;
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

    public List<UiMacroButton> getButtons() {
        return buttons;
    }

    public void setButtons(List<UiMacroButton> buttons) {
        this.buttons = buttons;
    }
}
