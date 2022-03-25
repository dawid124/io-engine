package pl.webd.dawid124.ioengine.rest.front;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.webd.dawid124.ioengine.config.MqttConfig;
import pl.webd.dawid124.ioengine.model.ActionRequest;
import pl.webd.dawid124.ioengine.service.StateService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class UserActionController {

    private final Gson gson = new Gson();

    private final MqttConfig.MqttGateway mqttGateway;
    private final StateService stateService;

    public UserActionController(MqttConfig.MqttGateway mqttGateway, StateService stateService) {
        this.mqttGateway = mqttGateway;
        this.stateService = stateService;
    }

    @PostMapping(value = "/api/actions", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ActionRequest allUp(@RequestBody ActionRequest action) {

        stateService.updateSate(action);

        mqttGateway.sendToMqtt(gson.toJson(action));

        return action;
    }
}
