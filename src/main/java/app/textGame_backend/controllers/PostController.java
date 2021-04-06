package app.textGame_backend.controllers;

import app.textGame_backend.entities.*;
import app.textGame_backend.repositories.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.geometry.Pos;
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
@RequestMapping(path="/post")
public class PostController{
    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VotesRepository votesRepository;

    @Autowired
    private UserVotesRepository userVotesRepository;

    Gson gson = new GsonBuilder().create();

    static final String SUCCESS_MSG = "Success";

    @GetMapping(path="/getAllPostOfThread", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getAllPostOfThread(@RequestParam int threadID, @RequestParam int currentUserID) {
        ArrayList<Post> commentList = postRepository.getComments(threadID);
        ArrayList<String> results = new ArrayList<>();
        if(commentList != null){
            for(Post post: commentList){
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(gson.toJson(post));
                arrayList.add(""+Optional.ofNullable(votesRepository.getVote(post.getPostID())).orElse(0));
                User creator = userRepository.getCreatorOfPost(post.getPostID());
                arrayList.add(gson.toJson(creator));
                arrayList.add(""+userVotesRepository.existingUserVote(currentUserID,post.getPostID()));
                results.add(gson.toJson(arrayList));
            }
            return gson.toJson(results);
        }
        return "failed";
    }

    @GetMapping(path="/upVote", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String upVote(@RequestParam int userID, @RequestParam int postID) {
        votesRepository.upvotePost(postID);
        if(userVotesRepository.existingUserVote(userID,postID) == null){
            userVotesRepository.save(new UserVotes(userID,postID,1));
        } else{
            userVotesRepository.addCurrentUserVote(userID,postID);
        }
        int votes = votesRepository.getVote(postID);
        return gson.toJson(votes);
    }

    @GetMapping(path="/downVote", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String downVote(@RequestParam int userID, @RequestParam int postID) {
        votesRepository.downvotePost(postID);
        if(userVotesRepository.existingUserVote(userID,postID) == null){
            userVotesRepository.save(new UserVotes(userID,postID,-1));
        } else{
            userVotesRepository.minusCurrentUserVote(userID,postID);
        }
        int votes = votesRepository.getVote(postID);
        return gson.toJson(votes);
    }

    @PostMapping(path="/addComment", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addComment(@RequestBody String reqBody) {
        JsonObject jo = gson.fromJson(reqBody, JsonObject.class);
        JsonElement postJE = jo.get("post");
        Post newPost = gson.fromJson(postJE, Post.class);
        if(newPost.getCreated() == null){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            newPost.setCreated(dtf.format(now));
        }

        Post addedPost = postRepository.save(newPost);
        votesRepository.save(new Votes(0, addedPost.getPostID(), addedPost.getThreadID()));
        return gson.toJson(addedPost);

    }

    @GetMapping(path="/getAllPostsCreatedByUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getAllPostsCreatedByUser(@RequestParam int id_user) {
        ArrayList<Post> arr = postRepository.getPostsCreatedByUser(id_user);
        if(arr == null) {
            arr = new ArrayList<>();
        }
        return gson.toJson(arr);
    }

}
