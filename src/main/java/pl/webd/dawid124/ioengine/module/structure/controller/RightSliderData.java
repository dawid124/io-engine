package pl.webd.dawid124.ioengine.module.structure.controller;

import pl.webd.dawid124.ioengine.module.structure.model.UiButtonGroup;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RightSliderData {
    private List<UiButtonGroup> rightBlindGroups;

    public RightSliderData(List<UiButtonGroup> rightBlindGroups) {
        this.rightBlindGroups = rightBlindGroups;
    }

    public List<UiButtonGroup> getRightBlindGroups() {
        return rightBlindGroups;
    }

    public void setRightBlindGroups(List<UiButtonGroup> rightBlindGroups) {
        this.rightBlindGroups = rightBlindGroups;
    }
}
