package pl.webd.dawid124.ioengine.api.user;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.webd.dawid124.ioengine.config.MqttConfig;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class UserActionController {

    private final MqttConfig.MqttGateway mqttGateway;

    public UserActionController(MqttConfig.MqttGateway mqttGateway) {
        this.mqttGateway = mqttGateway;
    }

    @PostMapping(value = "/api/actions", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpEntity<String> allUp(HttpEntity<String> httpEntity) {
        String json = httpEntity.getBody();

        mqttGateway.sendToMqtt(json);

        return httpEntity;
    }
}
