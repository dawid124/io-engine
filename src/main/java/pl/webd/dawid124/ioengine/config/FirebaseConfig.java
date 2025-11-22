package pl.webd.dawid124.ioengine.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            // Załaduj plik konfiguracyjny Firebase z resources
            InputStream serviceAccount = new ClassPathResource("firebase-service-account.json").getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            // Zainicjalizuj Firebase jeśli jeszcze nie został zainicjalizowany
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase został zainicjalizowany pomyślnie!");
            }
        } catch (IOException e) {
            System.err.println("Błąd podczas inicjalizacji Firebase: " + e.getMessage());
            System.err.println("UWAGA: Upewnij się, że plik firebase-service-account.json znajduje się w src/main/resources/");
            e.printStackTrace();
        }
    }
}
