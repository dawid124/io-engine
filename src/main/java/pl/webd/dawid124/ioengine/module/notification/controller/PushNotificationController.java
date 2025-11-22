package pl.webd.dawid124.ioengine.module.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.webd.dawid124.ioengine.module.notification.model.PushNotificationRequest;
import pl.webd.dawid124.ioengine.module.notification.service.PushNotificationService;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/push")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PushNotificationController {

    @Autowired
    private PushNotificationService pushNotificationService;

    /**
     * Endpoint do wysyłania notyfikacji push
     * POST http://localhost:8080/api/push/send
     * Body:
     * {
     *   "token": "token-urzadzenia",
     *   "title": "Tytuł",
     *   "body": "Treść notyfikacji"
     * }
     */
    @PostMapping(value = "/send", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, String>> sendNotification(@RequestBody PushNotificationRequest request) {
        Map<String, String> response = new HashMap<>();

        if (request.getToken() == null || request.getToken().isEmpty()) {
            response.put("status", "error");
            response.put("message", "Token urządzenia jest wymagany");
            return ResponseEntity.badRequest().body(response);
        }

        String result = pushNotificationService.sendNotification(
                request.getToken(),
                request.getTitle(),
                request.getBody(),
                request.getData()
        );

        if (result.startsWith("Error")) {
            response.put("status", "error");
            response.put("message", result);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } else {
            response.put("status", "success");
            response.put("messageId", result);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Endpoint do wysyłania notyfikacji do tematu
     * POST http://localhost:8080/api/push/send-to-topic
     * Body:
     * {
     *   "topic": "nazwa-tematu",
     *   "title": "Tytuł",
     *   "body": "Treść"
     * }
     */
    @PostMapping(value = "/send-to-topic", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> sendNotificationToTopic(
            @RequestParam String topic,
            @RequestParam String title,
            @RequestParam String body) {
        Map<String, String> response = new HashMap<>();

        String result = pushNotificationService.sendNotificationToTopic(topic, title, body);

        if (result.startsWith("Error")) {
            response.put("status", "error");
            response.put("message", result);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } else {
            response.put("status", "success");
            response.put("messageId", result);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Testowy endpoint do sprawdzenia czy API działa
     * GET http://localhost:8080/api/push/test
     */
    @PostMapping(value = "/test", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> test() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        response.put("message", "Push notification API is working!");
        return ResponseEntity.ok(response);
    }
}
