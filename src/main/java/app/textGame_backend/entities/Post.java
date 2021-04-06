package app.textGame_backend.entities;

import javax.persistence.*;

@Entity
@Table
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int postID;

    @Column(name="id_user")
    private int userID;

    @Column(name="thread_id")
    private int threadID;

    @Column(name="post_title")
    private String post_title;

    @Column(name="content")
    private String content;

    @Column(name="imageURL")
    private String imageURL;

    @Column(name="created")
    private String created;

    @Column(name="status")
    private int status;

    public Post() {
    }

    public Post(int postID, int userID, int threadID, String post_title, String content, String imageURL, String created, int status) {
        this.postID = postID;
        this.userID = userID;
        this.threadID = threadID;
        this.post_title = post_title;
        this.content = content;
        this.imageURL = imageURL;
        this.created = created;
        this.status = status;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getThreadID() {
        return threadID;
    }

    public void setThreadID(int threadID) {
        this.threadID = threadID;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
