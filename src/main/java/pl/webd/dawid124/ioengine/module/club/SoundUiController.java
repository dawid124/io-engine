package pl.webd.dawid124.ioengine.module.club;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.webd.dawid124.ioengine.module.action.model.rest.Color;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiActionRequest;
import pl.webd.dawid124.ioengine.module.club.model.SoundDefinitionUiResponse;
import pl.webd.dawid124.ioengine.module.club.model.SoundLightDefinition;
import pl.webd.dawid124.ioengine.module.state.model.rest.SceneStateResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SoundUiController {

    private final SoundDefinitionService soundDefinitionService;

    public SoundUiController(SoundDefinitionService soundDefinitionService) {
        this.soundDefinitionService = soundDefinitionService;
    }

    @GetMapping(value = "/api/sound", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<SoundDefinitionUiResponse> getUiData() {

        SoundDefinitionUiResponse response = new SoundDefinitionUiResponse(
                soundDefinitionService.getCurrentPreset(), soundDefinitionService.loadPresetsIds());

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/api/sound/preset/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<SoundDefinitionUiResponse> loadPreset(@PathVariable("id") String id) {

        soundDefinitionService.loadPreset(id);

        SoundDefinitionUiResponse response = new SoundDefinitionUiResponse(
                soundDefinitionService.getCurrentPreset(), soundDefinitionService.loadPresetsIds());

        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/api/sound/preset/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<SoundDefinitionUiResponse> saveAsPreset(@PathVariable("id") String id) {


        soundDefinitionService.saveCurrentAsPreset(id);
        soundDefinitionService.loadPreset(id);

        SoundDefinitionUiResponse response = new SoundDefinitionUiResponse(
                soundDefinitionService.getCurrentPreset(), soundDefinitionService.loadPresetsIds());

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/api/sound/preset", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<SoundDefinitionUiResponse> saveAsPreset() {

        soundDefinitionService.saveChangesInPreset();

        SoundDefinitionUiResponse response = new SoundDefinitionUiResponse(
                soundDefinitionService.getCurrentPreset(), soundDefinitionService.loadPresetsIds());

        return ResponseEntity.ok(response);
    }


    @PostMapping(value = "/api/sound/definition", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<SoundLightDefinition> preset(@RequestBody SoundLightDefinition newDefinition) {

        soundDefinitionService.updateDefinition(newDefinition);

        return ResponseEntity.ok(newDefinition);
    }


    @PostMapping(value = "sound/definition/all-color", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<SoundDefinitionUiResponse> preset(@RequestBody Color color) {

        soundDefinitionService.updateAllColorDefinitions(color);

        SoundDefinitionUiResponse response = new SoundDefinitionUiResponse(
                soundDefinitionService.getCurrentPreset(), soundDefinitionService.loadPresetsIds());

        return ResponseEntity.ok(response);
    }


}
