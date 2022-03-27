package pl.webd.dawid124.ioengine.rest.front;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.webd.dawid124.ioengine.config.MqttConfig;
import pl.webd.dawid124.ioengine.home.devices.output.EDeviceType;
import pl.webd.dawid124.ioengine.model.Action;
import pl.webd.dawid124.ioengine.model.ActionRequest;
import pl.webd.dawid124.ioengine.rest.blind.BlindService;
import pl.webd.dawid124.ioengine.service.StateService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserActionController {

    private final Gson gson = new Gson();

    private final MqttConfig.MqttGateway mqttGateway;
    private final StateService stateService;
    private final BlindService blindService;

    public UserActionController(MqttConfig.MqttGateway mqttGateway, StateService stateService, BlindService blindService) {
        this.mqttGateway = mqttGateway;
        this.stateService = stateService;
        this.blindService = blindService;
    }

    @PostMapping(value = "/api/actions", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public ActionRequest actions(@RequestBody ActionRequest action) {
        stateService.updateSate(action);

        List<Action> actions = action.getActions();

        List<Action> blindActions = actions.stream().filter(a -> EDeviceType.BLIND.equals(a.getIoType())).collect(Collectors.toList());
        blindService.processBlinds(blindActions);

        List<Action> lightsActions = actions.stream().filter(a -> !EDeviceType.BLIND.equals(a.getIoType())).collect(Collectors.toList());

        mqttGateway.sendToMqtt(gson.toJson(new ActionRequest(lightsActions)));

        return action;
    }
}
