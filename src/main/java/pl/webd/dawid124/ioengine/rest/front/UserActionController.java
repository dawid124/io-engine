package pl.webd.dawid124.ioengine.rest.front;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.webd.dawid124.ioengine.home.state.scene.SceneState;
import pl.webd.dawid124.ioengine.model.SceneStateResponse;
import pl.webd.dawid124.ioengine.model.UiActionRequest;
import pl.webd.dawid124.ioengine.service.StateService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserActionController {

    private final UserActionService userActionService;
    private final StateService stateService;

    public UserActionController(UserActionService userActionService, StateService stateService) {
        this.userActionService = userActionService;
        this.stateService = stateService;
    }

    @PostMapping(value = "/api/actions", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<SceneStateResponse> actions(@RequestBody UiActionRequest action) {
        userActionService.processActionChange(action);

        SceneState sceneState = stateService.fetchScene(action.getZoneId(), action.getSceneId());

        return ResponseEntity.ok(new SceneStateResponse(action.getZoneId(), sceneState));
    }
}
