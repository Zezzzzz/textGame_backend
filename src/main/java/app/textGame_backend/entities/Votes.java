package app.textGame_backend.entities;

import javax.persistence.*;

@Entity
@Table
public class Votes {
    @Column(name="vote_count")
    private int vote_count;

    @Id
    @Column(name="post_id")
    private int postId;

    @Column(name="thread_id")
    private int thread_id;

    public Votes() {
    }

    public Votes(int vote_count, int postId, int thread_id) {
        this.vote_count = vote_count;
        this.postId = postId;
        this.thread_id = thread_id;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getThread_id() {
        return thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }
}
