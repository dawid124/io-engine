package pl.webd.dawid124.ioengine.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "sound")
public class SoundProperties {

    private String presetLocation;

    public String getPresetLocation() {
        return presetLocation;
    }

    public void setPresetLocation(String presetLocation) {
        this.presetLocation = presetLocation;
    }
}
