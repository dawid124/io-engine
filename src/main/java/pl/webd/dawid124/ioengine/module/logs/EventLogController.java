package pl.webd.dawid124.ioengine.module.logs;

import org.springframework.web.bind.annotation.*;
import pl.webd.dawid124.ioengine.module.logs.model.EventLogResponse;
import pl.webd.dawid124.ioengine.module.logs.model.LogSearch;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class EventLogController {

    private final EventLogService service;

    public EventLogController(EventLogService service) {
        this.service = service;
    }

    @PostMapping(value = "/api/logs/fetch", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public EventLogResponse getLogs(@RequestBody LogSearch search) {
        return service.getLogs(search);
    }
}
