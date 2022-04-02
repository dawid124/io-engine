package pl.webd.dawid124.ioengine.rest.front;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.webd.dawid124.ioengine.home.state.scene.SceneState;
import pl.webd.dawid124.ioengine.model.SceneStateResponse;
import pl.webd.dawid124.ioengine.model.ZonesInitResponse;
import pl.webd.dawid124.ioengine.service.StateService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ZonesController {

    private final StateService stateService;
    private final UserActionService userActionService;

    public ZonesController(StateService stateService, UserActionService userActionService) {
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

    @GetMapping(value = "/api/zones", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ZonesInitResponse fetchZones() {
        return stateService.fetchZoneStates();
    }
}
