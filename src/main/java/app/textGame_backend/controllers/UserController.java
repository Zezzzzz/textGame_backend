package app.textGame_backend.controllers;
import app.textGame_backend.entities.ResponseMessage;
import app.textGame_backend.entities.User;
import app.textGame_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.google.gson.*;


@Controller
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    Gson gson = new GsonBuilder().create();

    static final String SUCCESS_MSG = "Success";

    @PostMapping(path="/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String userLogin(@RequestBody String reqBody) {
        JsonObject jo = gson.fromJson(reqBody, JsonObject.class);
        JsonElement userJE = jo.get("user");
        User newUser = gson.fromJson(userJE, User.class);
        User checkUser = checkUser(newUser.getId_token(), newUser.getEmail());
        if(checkUser == null){
            User registeredUser = userRepository.save(newUser); //insert the user details into database
            return gson.toJson(registeredUser);
        }
        return gson.toJson(checkUser);
    }

    @GetMapping(path="/checkUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User checkUser(@RequestParam String id_token, String email) {
        User user = userRepository.checkUserExistenceWithID(id_token, email);
        return user;
    }

}
