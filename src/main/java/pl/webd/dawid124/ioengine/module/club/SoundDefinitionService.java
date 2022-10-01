package pl.webd.dawid124.ioengine.module.club;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.config.settings.SoundProperties;
import pl.webd.dawid124.ioengine.module.action.model.rest.Color;
import pl.webd.dawid124.ioengine.module.club.model.SoundDefinition;
import pl.webd.dawid124.ioengine.module.club.model.SoundLightDefinition;
import pl.webd.dawid124.ioengine.utils.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SoundDefinitionService {

    private static final Logger LOG = LogManager.getLogger(SoundDefinitionService.class);
    public static final String DEFAULT_SOUND_DEFINITION = "classpath:defaultSoundDefinition.json";
    public static final String DEFAULT_EQ1 = "classpath:equalizer1.json";
    public static final String DEFAULT_EQ2 = "classpath:equalizer2.json";

    private final SoundProperties soundProperties;

    private SoundDefinition soundDefinition;

    private final Gson gson;

    public SoundDefinitionService(SoundProperties soundProperties) {
        this.soundProperties = soundProperties;
        this.gson = new GsonBuilder().create();
    }

    @PostConstruct
    public synchronized void init() {
        loadClasspath(DEFAULT_SOUND_DEFINITION);
    }

    private void loadClasspath(String name) {
        try {
            String def = ResourceUtils.getResourceFileAsString(name);
            soundDefinition = gson.fromJson(def, SoundDefinition.class);
//            presetSoundDefinition = gson.fromJson(def, SoundDefinition.class);
        } catch (IOException e) {
            LOG.error("Error on loading default sound preset", e);
        }
    }

    public List<String> loadPresetsIds() {
        ArrayList<String> list = new ArrayList<>();
        list.add(DEFAULT_SOUND_DEFINITION);
        list.add(DEFAULT_EQ1);
        list.add(DEFAULT_EQ2);

        try (Stream<Path> paths = Files.walk(Paths.get(soundProperties.getPresetLocation()))) {
            list.addAll(paths
                    .filter(Files::isRegularFile)
                    .map(f -> f.getFileName().toString())
                    .collect(Collectors.toList()));

        } catch (IOException e) {
            LOG.error("Error on loading preset in path: {}", soundProperties.getPresetLocation(), e);
        }

        return list;
    }

    public synchronized void loadPreset(String id) {
        if (id.startsWith("classpath")) {
            loadClasspath(id);
        } else {
            try {
                Path path = Paths.get(soundProperties.getPresetLocation(), id + ".json");
                byte[] fileContent = Files.readAllBytes(path);
                soundDefinition = gson.fromJson(new String(fileContent, StandardCharsets.UTF_8), SoundDefinition.class);
            } catch (IOException e) {
                LOG.error("Error on loading preset id: {}", id, e);
            }
        }

    }

    public void resetPreset() {
        loadPreset(getCurrentPreset().getId());
    }

    public void saveChangesInPreset() {
        saveCurrentAsPreset(getCurrentPreset().getId());
    }

    public void saveCurrentAsPreset(String newId) {
        Path path = Paths.get(soundProperties.getPresetLocation(), newId + ".json");

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

            writer.write(gson.toJson(getCurrentPreset()));

        } catch (IOException e) {
            LOG.error("Error on saving preset, id: {}", newId, e);
        }
    }

    public synchronized SoundDefinition getCurrentPreset() {
        return soundDefinition;
    }

    public void updateDefinition(SoundLightDefinition newDefinition) {
        getCurrentPreset().getLightDefinitions().stream()
                .filter(def -> def.getIoId().equals(newDefinition.getIoId())).findFirst()
                .ifPresent(soundLightDefinition -> soundLightDefinition.update(newDefinition));

    }

    public void updateAllColorDefinitions(Color color) {
        getCurrentPreset().getLightDefinitions()
                .forEach(def -> def.getColor().update(color));
    }
}
