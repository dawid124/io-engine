package pl.webd.dawid124.ioengine.module.action.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.webd.dawid124.ioengine.module.action.model.rest.*;
import pl.webd.dawid124.ioengine.module.automation.macro.MacroService;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.MotionSensorState;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.model.rest.SceneStateResponse;
import pl.webd.dawid124.ioengine.module.action.service.ActionService;
import pl.webd.dawid124.ioengine.module.state.service.StateService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserActionController {

    private final ActionService userActionService;
    private final MacroService macroService;
    private final StateService stateService;

    public UserActionController(ActionService userActionService, MacroService macroService, StateService stateService) {
        this.userActionService = userActionService;
        this.macroService = macroService;
        this.stateService = stateService;
    }

    @PostMapping(value = "/api/blinds", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<SceneStateResponse> blinds(@RequestBody UiActionRequest action) {

        userActionService.processActionChange(action);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/api/actions", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<SceneStateResponse> actions(@RequestBody UiActionRequest action) {

        userActionService.processActionChange(action);

        SceneState sceneState = stateService.fetchScene(action.getZoneId(), action.getSceneId());

        return ResponseEntity.ok(new SceneStateResponse(action.getZoneId(), sceneState));
    }

    @PostMapping(value = "/api/sensor-lock", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<UiSensorRequest> sensorLock(@RequestBody UiSensorRequest action) {

        MotionSensorState sensorState = (MotionSensorState) stateService.getSensors().get(action.getSensorId());
        sensorState.setLock(!action.isLock());
        action.setLock(!action.isLock());

        stateService.updateDeviceState(sensorState.getIoId(), sensorState);

        return ResponseEntity.ok(action);
    }

    @PostMapping(value = "/api/temperature", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<UiTemperatureChangeRequest> temperatureChange(@RequestBody UiTemperatureChangeRequest action) {

        stateService.getZoneState().get(action.getZoneId())
                .getTemperatureSceneStates().get(action.getSceneId())
                .getRanges().stream().filter(r -> action.getRangeId().equals(r.getId())).findFirst()
                .ifPresent(r -> r.setTemperature(action.getTemperature()));

        stateService.updateDbZone(stateService.getZoneState().get(action.getZoneId()));

        return ResponseEntity.ok(action);
    }

    @PostMapping(value = "/api/temperature-scene", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<UiTemperatureSceneChangeRequest> temperatureChange(@RequestBody UiTemperatureSceneChangeRequest action) {

        stateService.getZoneState().get(action.getZoneId()).setActiveTemperatureScene(action.getSceneId());

        stateService.updateDbZone(stateService.getZoneState().get(action.getZoneId()));

        return ResponseEntity.ok(action);
    }

    @PostMapping(value = "/api/macros", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<SceneStateResponse> macros(@RequestBody UiMacroRequest action) {
        macroService.runMacro(action.getVariable(), action.getId());
        return ResponseEntity.ok().build();
    }

}
