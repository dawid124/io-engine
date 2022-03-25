package pl.webd.dawid124.ioengine.rest.front;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.webd.dawid124.ioengine.home.structure.Home;
import pl.webd.dawid124.ioengine.service.StructureService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class HomeStructureController {

    private StructureService structureService;

    public HomeStructureController(StructureService structureService) {
        this.structureService = structureService;
    }

    @PostMapping(value = "/api/structure", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Home fetchStructure() {
        return structureService.fetchStructure();
    }
}
