package app.textGame_backend.entities;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    public String sendFirebaseMessage(Message message) {

        String response = "";
        try {
            response = FirebaseMessaging.getInstance().send(message);
            return response;
        } catch (FirebaseMessagingException e) {
            System.out.println("Error sending Firebase message: " + e.getMessage());
            return "Failed to send";
        }
    }
}
