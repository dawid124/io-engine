package pl.webd.dawid124.ioengine.module.voice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.webd.dawid124.ioengine.module.action.model.rest.EActionType;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.action.service.ActionService;
import pl.webd.dawid124.ioengine.module.automation.macro.MacroService;
import pl.webd.dawid124.ioengine.module.structure.service.StructureService;
import pl.webd.dawid124.ioengine.module.voice.dto.Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VoiceController {

    public static final String FLOOR_1 = "floor1";
    private final ActionService actionService;
    private final StructureService structureService;
    private final MacroService macroService;


    public VoiceController(ActionService actionService, StructureService structureService, MacroService macroService) {
        this.actionService = actionService;
        this.structureService = structureService;
        this.macroService = macroService;
    }

    @PostMapping(value = "/voice", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<VoiceResponse> macros(@RequestBody VoiceRequest request) {

        switch (request.getIntent().getName()) {
            case "ChangeScene":
                changeScene(request.getEntities());
                break;
            case "ChangeLight":
                changeLight(request.getEntities());
                break;
            case "MacroRun":
                macroRun(request.getEntities());
                break;
            case "MoveBlind":
                moveBlind(request.getEntities());
                break;
        }

        return ResponseEntity.ok(VoiceResponse.text("done"));
    }

    private void moveBlind(List<Entities> entities) {
        Entities mode = entities.stream().filter(e -> "mode".equals(e.getEntity())).findFirst().orElse(null);
        Entities percent = entities.stream().filter(e -> "percent".equals(e.getEntity())).findFirst().orElse(null);


        ArrayList<UiAction> list = new ArrayList<>();

        structureService.fetchStructure().getZones().get(FLOOR_1).getBlinds().forEach(b -> {
            UiAction uiAction = new UiAction();
            uiAction.setIoId(b.getIoId());
            if (percent != null) uiAction.setPercent(Integer.parseInt(percent.getValue()));
            uiAction.setAction(EActionType.valueOf(mode.getValue()));
            list.add(uiAction);
        });
        actionService.processBlinds(list);
    }

    private void macroRun(List<Entities> entities) {
        Entities id = entities.stream().filter(e -> "id".equals(e.getEntity())).findFirst().orElse(null);


        macroService.runMacro(new HashMap<>(), id.getValue());
    }

    private void changeLight(List<Entities> entities) {
        Entities percent = entities.stream().filter(e -> "percent".equals(e.getEntity())).findFirst().orElse(null);

        int percentInt = Integer.parseInt(percent.getValue());

        actionService.chngeLightPercent(FLOOR_1, percentInt);
    }

    public void changeScene(List<Entities> entities) {
        Entities nameEntities = entities.get(0);

        String sceneName = nameEntities.getValue();

        actionService.processSceneChange(FLOOR_1, sceneName);
    }
}
