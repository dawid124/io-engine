package pl.webd.dawid124.ioengine.module.club.model;

import java.util.List;

public class SoundDefinitionUiResponse {

    private SoundDefinition soundDefinition;

    private List<String> presets;

    public SoundDefinitionUiResponse(SoundDefinition soundDefinition, List<String> presets) {
        this.soundDefinition = soundDefinition;
        this.presets = presets;
    }

    public SoundDefinition getSoundDefinition() {
        return soundDefinition;
    }

    public void setSoundDefinition(SoundDefinition soundDefinition) {
        this.soundDefinition = soundDefinition;
    }

    public List<String> getPresets() {
        return presets;
    }

    public void setPresets(List<String> presets) {
        this.presets = presets;
    }
}
