package app.textGame_backend.entities;

import javax.persistence.*;

@Entity
@Table
public class Threads {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int threadID;

    @Column(name="thread_title")
    private String thread_title;

    @Column(name="id_user")
    private int userID;

    @Column(name="imageURL")
    private String imageURL;

    @Column(name="content")
    private String content;

    @Column(name="created")
    private String created;

    @Column(name="status")
    private int status;

    public Threads(int threadID, String thread_title, int userID, String imageURL, String content, String created, int status) {
        this.threadID = threadID;
        this.thread_title = thread_title;
        this.userID = userID;
        this.imageURL = imageURL;
        this.content = content;
        this.created = created;
        this.status = status;
    }

    public Threads() { }

    public int getThreadID() {
        return threadID;
    }

    public void setThreadID(int threadID) {
        this.threadID = threadID;
    }

    public String getThread_title() {
        return thread_title;
    }

    public void setThread_title(String thread_title) {
        this.thread_title = thread_title;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
