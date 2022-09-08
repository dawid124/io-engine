package pl.webd.dawid124.ioengine.module.state.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.model.rest.SceneStateResponse;
import pl.webd.dawid124.ioengine.module.state.model.rest.ZonesStateResponse;
import pl.webd.dawid124.ioengine.module.action.service.UserActionService;
import pl.webd.dawid124.ioengine.module.state.service.StateService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StateController {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    private final StateService stateService;
    private final UserActionService userActionService;

    public StateController(StateService stateService, UserActionService userActionService) {
        this.stateService = stateService;
        this.userActionService = userActionService;
    }

    @PostMapping(value = "/api/zone/{zoneId}/{sceneId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<SceneStateResponse> changeScene(@PathVariable("zoneId") String zoneId, @PathVariable("sceneId") String sceneId) {

        SceneState sceneState = userActionService.processSceneChange(zoneId, sceneId);

        return ResponseEntity.ok(new SceneStateResponse(zoneId, sceneState));
    }

    @PostMapping(value = "/api/zone/{zoneId}/{sceneId}/delay", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<SceneStateResponse> changeSceneDelay(@PathVariable("zoneId") String zoneId, @PathVariable("sceneId") String sceneId) {

        scheduler.schedule(() -> {
            userActionService.processSceneChange(zoneId, sceneId);
        }, 1, TimeUnit.MINUTES);

        return ResponseEntity.ok().build();
    }


    @PostMapping(value = "/api/zone/{zoneId}/{sceneId}/reset", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<SceneStateResponse> resetScene(@PathVariable("zoneId") String zoneId, @PathVariable("sceneId") String sceneId) {

        stateService.resetScene(zoneId, sceneId);
        SceneState sceneState = userActionService.processSceneChange(zoneId, sceneId);

        return ResponseEntity.ok(new SceneStateResponse(zoneId, sceneState));
    }

    @GetMapping(value = "/api/zones", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ZonesStateResponse fetchZones() {
        return stateService.fetchZoneStatesResponse();
    }
}
