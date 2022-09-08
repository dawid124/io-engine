package pl.webd.dawid124.ioengine.module.club.model;

import java.util.List;

public class SoundDefinition {

    private String id;

    private List<SoundLightDefinition> lightDefinitions;

    public List<SoundLightDefinition> getLightDefinitions() {
        return lightDefinitions;
    }

    public void setLightDefinitions(List<SoundLightDefinition> lightDefinitions) {
        this.lightDefinitions = lightDefinitions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SoundDefinition that = (SoundDefinition) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return lightDefinitions != null ? lightDefinitions.equals(that.lightDefinitions) : that.lightDefinitions == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (lightDefinitions != null ? lightDefinitions.hashCode() : 0);
        return result;
    }
}
