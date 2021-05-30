package app.textGame_backend.controllers;
import app.textGame_backend.entities.ResponseMessage;
import app.textGame_backend.entities.Threads;
import app.textGame_backend.entities.User;
import app.textGame_backend.repositories.PostRepository;
import app.textGame_backend.repositories.ThreadRepository;
import app.textGame_backend.repositories.UserRepository;
import app.textGame_backend.repositories.UserVotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.google.gson.*;

import java.util.ArrayList;


@Controller
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserVotesRepository userVotesRepository;

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
        System.out.println(gson.toJson(checkUser));
        return gson.toJson(checkUser);
    }

    @GetMapping(path="/checkUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User checkUser(@RequestParam String id_token, String email) {
        User user = userRepository.checkUserExistenceWithID(id_token, email);
        return user;
    }

    @GetMapping(path="/checkUserStats", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public int[] checkUserStats(@RequestParam int user_id) {
        int[] res = new int[3];
        res[0] = threadRepository.getThreadsCreatedByUser(user_id, 10, 0).size();
        res[1] = postRepository.getPostsCreatedByUser(user_id,0,0).size();
        res[2] = userVotesRepository.userVotes(user_id);
        return res;
    }

}
