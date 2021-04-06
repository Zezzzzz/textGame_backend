package app.textGame_backend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class UserVotes {
    @Column(name="user_id")
    private int userId;
    @Id
    @Column(name="post_id")
    private int postId;

    @Column(name="vote")
    private int vote;

    public UserVotes() {
    }

    public UserVotes(int userId, int postId, int vote) {
        this.userId = userId;
        this.postId = postId;
        this.vote = vote;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
