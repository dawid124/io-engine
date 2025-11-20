package pl.webd.dawid124.ioengine.module.voice;

import pl.webd.dawid124.ioengine.module.action.model.EChangeLightMode;
import pl.webd.dawid124.ioengine.module.action.model.rest.EActionType;
import pl.webd.dawid124.ioengine.module.action.model.rest.IUiAction;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.action.service.ActionService;
import pl.webd.dawid124.ioengine.module.automation.macro.MacroService;
import pl.webd.dawid124.ioengine.module.structure.service.StructureService;
import pl.webd.dawid124.ioengine.module.voice.dto.Entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VoiceController {

    public static final String FLOOR_1 = "floor1";
    private final Gson gson = new Gson();
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
    public ResponseEntity<VoiceResponse> macros(@RequestBody VoiceRequestMsg request) {
        String text = handleMessage(request);
        return ResponseEntity.ok(new VoiceResponse(text));
    }


    public String handleMessage(VoiceRequestMsg msg) throws MessagingException {

        Optional<String> siteIdExists = this.structureService.fetchStructure()
            .getZones()
            .keySet()
            .stream()
            .filter(key -> key.equals(msg.getSiteId()))
            .findAny();

        if (!siteIdExists.isPresent()) {
            return "Nie ma takiego siteID";
        }

        List<Entities> entities = msg.getSlots().stream()
                .map(s -> new Entities(s.getEntity(), s.getValue().getValue()))
                .collect(Collectors.toList());

        String output = "";

        switch (msg.getIntent().getIntentName()) {
            case "ChangeScene":
                changeScene(entities, msg.getSiteId());
                break;
            case "ChangeSceneZone":

                break;
            case "ChangeAllLight":
                changeAllLight(entities, msg.getSiteId());
                break;
            case "ChangeIdLightOnOff":
                changeIdLightOnOff(entities, msg.getSiteId());
                break;
            case "IncreaseLight":
                increaseLight(entities, msg.getSiteId());
                break;
            case "DecreaseLight":
                decreaseLight(entities, msg.getSiteId());
                break;
            case "MacroRun":
                macroRun(entities);
                break;
            case "MoveBlind":
                moveBlind(entities, msg.getSiteId());
                break;
            case "GetTemperature":
                output = getTemperature(msg.getSiteId());
                break;
            case "GetTime":
                output = "Jest " + new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                break;
            default:
                return "";
        }

        return output;
    }


    private void moveBlind(List<Entities> entities, String zoneId) {
        Entities mode = entities.stream().filter(e -> "mode".equals(e.getEntity())).findFirst().orElse(null);
        Entities percent = entities.stream().filter(e -> "percent".equals(e.getEntity())).findFirst().orElse(null);


        ArrayList<IUiAction> list = new ArrayList<>();

        structureService.fetchStructure().getZones().get(zoneId).getBlinds().forEach(b -> {
            UiAction uiAction = new UiAction();
            uiAction.setIoId(b.getIoId());
            if (percent != null) {
                uiAction.setPercent(Integer.parseInt(percent.getValue()));
            } else {
                uiAction.setPercent(25);
            }
            uiAction.setAction(EActionType.valueOf(mode.getValue()));
            list.add(uiAction);
        });
        actionService.processBlinds(list);
    }

    private void macroRun(List<Entities> entities) {
        Entities id = entities.stream().filter(e -> "id".equals(e.getEntity())).findFirst().orElse(null);


        macroService.runMacro(new HashMap<>(), id.getValue());
    }

    private void changeAllLight(List<Entities> entities, String siteId) {
        Entities percent = entities.stream().filter(e -> "percent".equals(e.getEntity())).findFirst().orElse(null);

        int percentInt = Integer.parseInt(percent.getValue());

        actionService.changeLightPercent(siteId, percentInt, EChangeLightMode.SET);
    }

    private void changeIdLightOnOff(List<Entities> entities, String siteId) {
        Entities mode = entities.stream().filter(e -> "mode".equals(e.getEntity())).findFirst().orElse(null);
        Entities ioId = entities.stream().filter(e -> "ioId".equals(e.getEntity())).findFirst().orElse(null);


        actionService.changeIdLightOnOff(siteId, mode.getValue(), ioId.getValue());
    }

    private void increaseLight(List<Entities> entities, String siteId) {
        Entities percent = entities.stream().filter(e -> "percent".equals(e.getEntity())).findFirst().orElse(null);

        int percentInt = Integer.parseInt(percent.getValue());

        actionService.changeLightPercent(siteId, percentInt, EChangeLightMode.INCREASE);
    }

    private void decreaseLight(List<Entities> entities, String siteId) {
        Entities percent = entities.stream().filter(e -> "percent".equals(e.getEntity())).findFirst().orElse(null);

        int percentInt = Integer.parseInt(percent.getValue());

        actionService.changeLightPercent(siteId, percentInt, EChangeLightMode.DECREASE);
    }

    public void changeScene(List<Entities> entities, String siteId) {
        Entities nameEntities = entities.get(0);

        String sceneName = nameEntities.getValue();

        actionService.processSceneChange(siteId, sceneName);
    }

    private String getTemperature(String siteId) {
        return actionService.getTemperature(siteId);
    }
}
