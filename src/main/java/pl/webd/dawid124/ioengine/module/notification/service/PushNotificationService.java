package pl.webd.dawid124.ioengine.module.notification.service;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PushNotificationService {

    /**
     * Wysyła notyfikację push do jednego urządzenia
     *
     * @param deviceToken Token FCM urządzenia docelowego
     * @param title       Tytuł notyfikacji
     * @param body        Treść notyfikacji
     * @return ID wiadomości lub komunikat błędu
     */
    public String sendNotification(String deviceToken, String title, String body) {
        return sendNotification(deviceToken, title, body, null);
    }

    /**
     * Wysyła notyfikację push do jednego urządzenia z dodatkowymi danymi
     *
     * @param deviceToken Token FCM urządzenia docelowego
     * @param title       Tytuł notyfikacji
     * @param body        Treść notyfikacji
     * @param data        Dodatkowe dane (opcjonalne)
     * @return ID wiadomości lub komunikat błędu
     */
    public String sendNotification(String deviceToken, String title, String body, Map<String, String> data) {
        try {
            // Budowanie notyfikacji
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            // Budowanie wiadomości
            Message.Builder messageBuilder = Message.builder()
                    .setToken(deviceToken)
                    .setNotification(notification);

            // Dodanie dodatkowych danych jeśli są dostępne
            if (data != null && !data.isEmpty()) {
                messageBuilder.putAllData(data);
            }

            Message message = messageBuilder.build();

            // Wysłanie wiadomości
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Pomyślnie wysłano notyfikację: " + response);
            return response;

        } catch (FirebaseMessagingException e) {
            System.err.println("Błąd podczas wysyłania notyfikacji: " + e.getMessage());
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Wysyła notyfikację do wielu urządzeń jednocześnie
     *
     * @param deviceTokens Lista tokenów FCM urządzeń
     * @param title        Tytuł notyfikacji
     * @param body         Treść notyfikacji
     * @return Liczba pomyślnie wysłanych notyfikacji
     */
    public int sendNotificationToMultipleDevices(List<String> deviceTokens, String title, String body) {
        try {
            // Budowanie notyfikacji
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            // Budowanie wiadomości multicast
            MulticastMessage message = MulticastMessage.builder()
                    .setNotification(notification)
                    .addAllTokens(deviceTokens)
                    .build();

            // Wysłanie wiadomości
            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            System.out.println("Pomyślnie wysłano " + response.getSuccessCount() + " z " + deviceTokens.size() + " notyfikacji");

            return response.getSuccessCount();

        } catch (FirebaseMessagingException e) {
            System.err.println("Błąd podczas wysyłania notyfikacji: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Wysyła notyfikację do tematu (topic)
     * Użyteczne gdy wiele urządzeń subskrybuje ten sam temat
     *
     * @param topic Nazwa tematu
     * @param title Tytuł notyfikacji
     * @param body  Treść notyfikacji
     * @return ID wiadomości lub komunikat błędu
     */
    public String sendNotificationToTopic(String topic, String title, String body) {
        try {
            // Budowanie notyfikacji
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            // Budowanie wiadomości do tematu
            Message message = Message.builder()
                    .setTopic(topic)
                    .setNotification(notification)
                    .build();

            // Wysłanie wiadomości
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Pomyślnie wysłano notyfikację do tematu '" + topic + "': " + response);
            return response;

        } catch (FirebaseMessagingException e) {
            System.err.println("Błąd podczas wysyłania notyfikacji do tematu: " + e.getMessage());
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
