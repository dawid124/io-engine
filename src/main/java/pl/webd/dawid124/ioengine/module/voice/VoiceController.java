package pl.webd.dawid124.ioengine.module.voice;

import com.google.gson.Gson;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MutableMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.*;
import pl.webd.dawid124.ioengine.config.MqttConfig;
import pl.webd.dawid124.ioengine.module.action.model.EChangeLightMode;
import pl.webd.dawid124.ioengine.module.action.model.rest.EActionType;
import pl.webd.dawid124.ioengine.module.action.model.rest.IUiAction;
import pl.webd.dawid124.ioengine.module.action.model.rest.UiAction;
import pl.webd.dawid124.ioengine.module.action.service.ActionService;
import pl.webd.dawid124.ioengine.module.automation.macro.MacroService;
import pl.webd.dawid124.ioengine.module.structure.service.StructureService;
import pl.webd.dawid124.ioengine.module.voice.dto.Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VoiceController implements MessageHandler {

    public static final String FLOOR_1 = "floor1";
    private final Gson gson = new Gson();
    private final ActionService actionService;
    private final StructureService structureService;
    private final MacroService macroService;
    private final MqttConfig.MqttGateway mqttGateway;



    public VoiceController(ActionService actionService, StructureService structureService, MacroService macroService,
                           MqttConfig.MqttGateway mqttGateway) {
        this.actionService = actionService;
        this.structureService = structureService;
        this.macroService = macroService;
        this.mqttGateway = mqttGateway;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        VoiceRequestMsg msg = gson.fromJson((String) message.getPayload(), VoiceRequestMsg.class);

        List<Entities> entities = msg.getSlots().stream()
                .map(s -> new Entities(s.getEntity(), s.getValue().getValue()))
                .collect(Collectors.toList());

        switch (msg.getIntent().getIntentName()) {
            case "ChangeScene":
                changeScene(entities);
                break;
            case "ChangeLight":
                changeLight(entities);
                break;
            case "IncreaseLight":
                increaseLight(entities);
                break;
            case "DecreaseLight":
                decreaseLight(entities);
                break;
            case "MacroRun":
                macroRun(entities);
                break;
            case "MoveBlind":
                moveBlind(entities);
                break;
        }

        sendResponse(new VoiceResponseMsg("done", msg.getSiteId()));
    }

    public void sendResponse(VoiceResponseMsg responseMsg) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>() {{
            put(MqttHeaders.TOPIC, "hermes/tts/say");
        }};

        MutableMessage<String> message = new MutableMessage<>(gson.toJson(responseMsg), hashMap);

        mqttGateway.sendToMqtt(message);
    }

//    @PostMapping(value = "/voice", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
//    @CrossOrigin(origins = "*", allowedHeaders = "*")
//    @ResponseBody
//    public ResponseEntity<VoiceResponse> macros(@RequestBody VoiceRequest request) {
//
//        switch (request.getIntent().getName()) {
//            case "ChangeScene":
//                changeScene(request.getEntities());
//                break;
//            case "ChangeLight":
//                changeLight(request.getEntities());
//                break;
//            case "MacroRun":
//                macroRun(request.getEntities());
//                break;
//            case "MoveBlind":
//                moveBlind(request.getEntities());
//                break;
//        }
//
//        return ResponseEntity.ok(VoiceResponse.text("done"));
//    }

    private void moveBlind(List<Entities> entities) {
        Entities mode = entities.stream().filter(e -> "mode".equals(e.getEntity())).findFirst().orElse(null);
        Entities percent = entities.stream().filter(e -> "percent".equals(e.getEntity())).findFirst().orElse(null);


        ArrayList<IUiAction> list = new ArrayList<>();

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

        actionService.changeLightPercent(FLOOR_1, percentInt, EChangeLightMode.SET);
    }

    private void increaseLight(List<Entities> entities) {
        Entities percent = entities.stream().filter(e -> "percent".equals(e.getEntity())).findFirst().orElse(null);

        int percentInt = Integer.parseInt(percent.getValue());

        actionService.changeLightPercent(FLOOR_1, percentInt, EChangeLightMode.INCREASE);
    }

    private void decreaseLight(List<Entities> entities) {
        Entities percent = entities.stream().filter(e -> "percent".equals(e.getEntity())).findFirst().orElse(null);

        int percentInt = Integer.parseInt(percent.getValue());

        actionService.changeLightPercent(FLOOR_1, percentInt, EChangeLightMode.DECREASE);
    }

    public void changeScene(List<Entities> entities) {
        Entities nameEntities = entities.get(0);

        String sceneName = nameEntities.getValue();

        actionService.processSceneChange(FLOOR_1, sceneName);
    }
}
