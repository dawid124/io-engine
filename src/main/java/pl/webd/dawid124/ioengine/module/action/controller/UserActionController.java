package pl.webd.dawid124.ioengine.module.action.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.model.rest.SceneStateResponse;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiActionRequest;
import pl.webd.dawid124.ioengine.module.action.service.UserActionService;
import pl.webd.dawid124.ioengine.module.state.service.StateService;

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
}
