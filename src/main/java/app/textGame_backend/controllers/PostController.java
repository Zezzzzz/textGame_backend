package app.textGame_backend.controllers;

import app.textGame_backend.entities.*;
import app.textGame_backend.repositories.*;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
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
import java.util.Map;
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

    MessageController messageController;

    Gson gson = new GsonBuilder().create();

    static final String SUCCESS_MSG = "Success";

    @GetMapping(path="/allPostOfThread", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getAllPostOfThread(@RequestParam int threadID, @RequestParam int currentUserID, @RequestParam String category, @RequestParam int limit, @RequestParam int offset) {
        ArrayList<Post> commentList = new ArrayList<>();
        //System.out.println(limit + " " + offset + " " + category);
        if(category.equals("latest")) {
            commentList = postRepository.getComments(threadID,limit,offset);
        } else if(category.equals("top")){
            commentList =postRepository.getCommentsByVotes(threadID,limit,offset);
        }
        ArrayList<String> results = new ArrayList<>();
        //System.out.println(gson.toJson(commentList));
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
    public String upVote(@RequestParam int userID, @RequestParam int postID, @RequestParam int threadID) {
        if(votesRepository.getVote(postID) != null) {
            votesRepository.upvotePost(postID);
        } else{
            votesRepository.newUpvotePost(postID,threadID);
        }
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
    public String downVote(@RequestParam int userID, @RequestParam int postID, @RequestParam int threadID) {
        if(votesRepository.getVote(postID) != null) {
            votesRepository.downvotePost(postID);
        } else{
            votesRepository.newDownvotePost(postID,threadID);
        }
        if(userVotesRepository.existingUserVote(userID,postID) == null){
            userVotesRepository.save(new UserVotes(userID,postID,-1));
        } else{
            userVotesRepository.minusCurrentUserVote(userID,postID);
        }
        int votes = votesRepository.getVote(postID);

        return gson.toJson(votes);
    }

    @PostMapping(path="/removeOwnComment", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String removeOwnComment(@RequestBody String reqBody) {
        JsonObject jo = gson.fromJson(reqBody, JsonObject.class);
        int postID = jo.get("postID").getAsInt();
        int userID = jo.get("userID").getAsInt();
        if(postRepository.getPostById(postID).getUserID() == userID){
            userVotesRepository.deleteUserVotesByPostId(postID);
            votesRepository.deleteVotesByPostId(postID);
            postRepository.deletePostById(postID);
            return "success";
        }
        return "failed";

    }

    @PostMapping(path="/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addComment(@RequestBody String reqBody) {
        JsonObject jo = gson.fromJson(reqBody, JsonObject.class);
        JsonElement postJE = jo.get("post");
        Post newPost = gson.fromJson(postJE, Post.class);
        MessageService messageService = new MessageService();
        messageController = new MessageController(messageService);
        if(newPost.getCreated() == null){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            newPost.setCreated(dtf.format(now));
        }
        System.out.println(reqBody);
        Post addedPost = postRepository.save(newPost);
        votesRepository.save(new Votes(0, addedPost.getPostID(), addedPost.getThreadID()));
        String registrationToken = userRepository.getCreatorOfThread(addedPost.getThreadID()).getFirebaseToken();
        Notification notification = Notification.builder()
                .setTitle("Thread commented")
                .setBody(userRepository.getCreatorOfPost(addedPost.getPostID()).getUsername() + " commented on \"" + threadRepository.getThreadWithId(addedPost.getThreadID()).getThread_title() +"\"")
                .build();
        Message message = Message.builder()
                .setNotification(notification)
                .putData("data", "threadID="+addedPost.getThreadID())
                .setToken(registrationToken)
                .build();
        messageController.sendMessage(message);
        return gson.toJson(addedPost);

    }

    @GetMapping(path="/allPostsCreatedByUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getAllPostsCreatedByUser(@RequestParam int id_user, @RequestParam int limit, @RequestParam int offset) {
        ArrayList<Post> arr = postRepository.getPostsCreatedByUser(id_user,limit,offset);
        if(arr == null) {
            arr = new ArrayList<>();
        }
        return gson.toJson(arr);
    }

    @PostMapping(path="/editComment", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String editComment(@RequestBody String reqBody) {
        JsonObject jo = gson.fromJson(reqBody, JsonObject.class);
        //System.out.println(jo);
        String content = jo.get("content").getAsString();
        int postID = jo.get("postID").getAsInt();
        try {
            postRepository.editComment(content, postID);
            return "success";
        } catch (Exception e){
            return "failed";
        }
    }

}
