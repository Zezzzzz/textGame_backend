package app.textGame_backend.controllers;

import app.textGame_backend.entities.MessageService;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/message")
public class MessageController {
    private MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @RequestMapping("/send-message")
    public String sendMessage(Message message) {
        return service.sendFirebaseMessage(message);
    }
}
