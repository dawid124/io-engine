package pl.webd.dawid124.ioengine.module.state.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.webd.dawid124.ioengine.module.action.model.VarChangeRequest;
import pl.webd.dawid124.ioengine.module.automation.macro.json.IVariableJsonAdapter;
import pl.webd.dawid124.ioengine.module.state.model.scene.SceneState;
import pl.webd.dawid124.ioengine.module.state.model.rest.SceneStateResponse;
import pl.webd.dawid124.ioengine.module.state.model.rest.ZonesStateResponse;
import pl.webd.dawid124.ioengine.module.action.service.ActionService;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.service.StateService;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StateController {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    private final StateService stateService;
    private final ActionService userActionService;
    private final Gson gson;

    public StateController(StateService stateService, ActionService userActionService) {
        this.stateService = stateService;
        this.userActionService = userActionService;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(IVariable.class, new IVariableJsonAdapter())
                .create();
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

    @PostMapping(value = "/api/var", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<VarChangeRequest> var(@RequestBody String change) {

        VarChangeRequest varChangeRequest = gson.fromJson(change, VarChangeRequest.class);
        stateService.changeStateVar(varChangeRequest);

        return ResponseEntity.ok(varChangeRequest);
    }

    @PreDestroy
    public void destructor() {
        scheduler.shutdown();
    }
}
