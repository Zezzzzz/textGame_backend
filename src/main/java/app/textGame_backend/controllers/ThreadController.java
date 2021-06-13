package app.textGame_backend.controllers;

import app.textGame_backend.entities.Threads;
import app.textGame_backend.entities.User;
import app.textGame_backend.repositories.PostRepository;
import app.textGame_backend.repositories.ThreadRepository;
import app.textGame_backend.repositories.UserRepository;
import app.textGame_backend.repositories.VotesRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping(path="/thread")
public class ThreadController{
    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VotesRepository votesRepository;



    Gson gson = new GsonBuilder().create();

    static final String SUCCESS_MSG = "Success";

    @GetMapping(path="/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getLatestThreads(@RequestParam int limit, @RequestParam int offset) {
        ArrayList<Threads> threadsList = threadRepository.getNewestThread(limit,offset);
        if(threadsList != null){
            ArrayList<String> results = new ArrayList<>();
            for(Threads thread: threadsList){
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(gson.toJson(thread));
                arrayList.add(""+Optional.of(postRepository.getComments(thread.getThreadID(),0,0).size()).orElse(0));
                arrayList.add(""+Optional.ofNullable(votesRepository.threadVotes(thread.getThreadID())).orElse(0));
                results.add(gson.toJson(arrayList));
            }
            return gson.toJson(results);
        }
        return "failed";
    }

    @GetMapping(path="/listOfThreads", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getListOfThreads(@RequestParam String category, @RequestParam int limit, @RequestParam int offset) {
        ArrayList<Threads> threadsList;
        if(category.equals("reco")){
            threadsList = threadRepository.getRecommendedThread(limit,offset);
        } else if(category.equals("new")){
            threadsList = threadRepository.getNewestThread(limit,offset);
        } else {
            threadsList = threadRepository.getMostPopThread(limit,offset);
        }
        if(threadsList != null){
            ArrayList<String> results = new ArrayList<>();
            for(Threads thread: threadsList){
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(gson.toJson(thread));
                arrayList.add(""+postRepository.getNumberOfCommentsInThread(thread.getThreadID()));
                arrayList.add(""+Optional.ofNullable(votesRepository.threadVotes(thread.getThreadID())).orElse(0));
                results.add(gson.toJson(arrayList));
            }
            return gson.toJson(results);

        }
        return "failed";
    }

    @GetMapping(path="/thread", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getThread(@RequestParam int threadID) {
        Threads thread = threadRepository.getThreadWithId(threadID);
        User user = userRepository.getCreatorOfThread(threadID);
        HashMap<String, String> map = new HashMap<>();
        if(thread != null && user != null){
            map.put(gson.toJson(user), gson.toJson(thread));
            return gson.toJson(map);
        }
        return "Not Found";
    }

    @GetMapping(path="/allThreadsCreatedByUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getAllThreadsCreatedByUser(@RequestParam int id_user, @RequestParam int limit, @RequestParam int offset) {
        ArrayList<Threads> arr = threadRepository.getThreadsCreatedByUser(id_user,limit,offset);
        if(arr == null) {
            return "failed";
        } else{
            ArrayList<String> results = new ArrayList<>();
            for(Threads thread: arr){
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(gson.toJson(thread));
                arrayList.add(""+postRepository.getNumberOfCommentsInThread(thread.getThreadID()));
                arrayList.add(""+Optional.ofNullable(votesRepository.threadVotes(thread.getThreadID())).orElse(0));
                results.add(gson.toJson(arrayList));
            }
            return gson.toJson(results);
        }
    }

    @GetMapping(path="/allThreadsCommentedByUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getAllThreadsCommentedByUser(@RequestParam int id_user, @RequestParam int limit, @RequestParam int offset) {
        ArrayList<Threads> arr = threadRepository.getAllThreadsWithCommentsByUser(id_user,limit,offset);
        if(arr == null) {
            return "failed";
        } else{
            ArrayList<String> results = new ArrayList<>();
            for(Threads thread: arr){
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(gson.toJson(thread));
                arrayList.add(""+postRepository.getNumberOfCommentsInThread(thread.getThreadID()));
                arrayList.add(""+Optional.ofNullable(votesRepository.threadVotes(thread.getThreadID())).orElse(0));
                results.add(gson.toJson(arrayList));
            }
            return gson.toJson(results);
        }
    }

    @PostMapping(path="/newThread", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addNewThread(@RequestBody String reqBody) {
        JsonObject jo = gson.fromJson(reqBody, JsonObject.class);
        JsonElement threadJE = jo.get("thread");
        Threads newThread = gson.fromJson(threadJE, Threads.class);
        if(newThread.getCreated() == null){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            newThread.setCreated(dtf.format(now));
        }

        Threads addedThread = threadRepository.save(newThread);
        return gson.toJson(addedThread);

    }
}
