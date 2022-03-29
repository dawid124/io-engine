package pl.webd.dawid124.ioengine.rest.front;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.webd.dawid124.ioengine.home.structure.Home;
import pl.webd.dawid124.ioengine.model.ZoneChangeResponse;
import pl.webd.dawid124.ioengine.model.ZonesResponse;
import pl.webd.dawid124.ioengine.service.StateService;
import pl.webd.dawid124.ioengine.service.StructureService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ZonesController {

    private final StateService stateService;

    public ZonesController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping(value = "/api/zone/{zoneId}/{sceneId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<ZoneChangeResponse> changeScene(@PathVariable("zoneId") String zoneId, @PathVariable("sceneId") String sceneId) {

        stateService.updateScene(zoneId, sceneId);

        return ResponseEntity.ok(new ZoneChangeResponse(zoneId, sceneId));
    }

    @GetMapping(value = "/api/zones", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ZonesResponse fetchZones() {
        return stateService.fetchZones();
    }
}
