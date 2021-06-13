package app.textGame_backend;

import com.google.api.client.util.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
public class TextGameBackendApplication {
	@Value("${app.firebase-config}")
	private String firebaseConfig;

	public static void main(String[] args) {
		SpringApplication.run(TextGameBackendApplication.class, args);
		initializeFirebaseAdmin();
	}


	private static void initializeFirebaseAdmin() {
		try {
			Resource resource = new ClassPathResource("firebase-service-account.json");
			GoogleCredentials googleCredentials = GoogleCredentials
					.fromStream(resource.getInputStream());
			FirebaseOptions firebaseOptions = FirebaseOptions
					.builder()
					.setCredentials(googleCredentials)
					.build();

			FirebaseApp.initializeApp(firebaseOptions);
		} catch (FileNotFoundException e) {
			System.out.println("Firebase key not found! Firebase Admin functionality will not work.");
		} catch (IOException e) {
			System.out.println("Error initialising Firebase Admin! Firebase Admin functionality will not work.");
		}
	}
}
