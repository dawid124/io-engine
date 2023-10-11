package pl.webd.dawid124.ioengine.module.structure.controller;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.webd.dawid124.ioengine.module.structure.model.Home;
import pl.webd.dawid124.ioengine.module.structure.service.StructureService;

import java.util.concurrent.TimeUnit;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HomeStructureController {

    private StructureService structureService;

    public HomeStructureController(StructureService structureService) {
        this.structureService = structureService;
    }

    @GetMapping(value = "/api/structure", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<Home> fetchStructure() {
        CacheControl cacheControl = CacheControl.maxAge(5, TimeUnit.DAYS)
                .noTransform()
                .mustRevalidate();
        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(structureService.fetchStructure());
    }
}
