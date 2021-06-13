package app.textGame_backend.entities;

import javax.persistence.*;

@Entity
@Table
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int userID;

    @Column(name="username")
    private String username;

    @Column(name="email")
    private String email;

    @Column(name="is_moderator")
    private boolean is_moderator;

    @Column(name="blocked")
    private boolean blocked;

    @Column(name="id_token")
    private String id_token;

    @Column(name="profile_pic")
    private String profile_pic;

    @Column(name="firebase_token")
    private String firebase_token;

    public User() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIs_moderator() {
        return is_moderator;
    }

    public void setIs_moderator(boolean is_moderator) {
        this.is_moderator = is_moderator;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getFirebaseToken() {
        return firebase_token;
    }

    public void setFirebaseToken(String firebase_token) {
        this.firebase_token = firebase_token;
    }

    public User(int userID, String username, String email, boolean is_moderator, boolean blocked, String id_token, String profile_pic, String firebase_token) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.is_moderator = is_moderator;
        this.blocked = blocked;
        this.id_token = id_token;
        this.profile_pic = profile_pic;
        this.firebase_token = firebase_token;
    }
}
