package pl.webd.dawid124.ioengine.module.structure.model;

import java.util.List;

public class ButtonGroup {

    private String id;
    private String label;
    private List<MacroButton> buttons;

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

    public List<MacroButton> getButtons() {
        return buttons;
    }

    public void setButtons(List<MacroButton> buttons) {
        this.buttons = buttons;
    }
}
