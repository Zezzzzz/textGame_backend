package app.textGame_backend.repositories;

import app.textGame_backend.entities.User;
import app.textGame_backend.entities.Votes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface VotesRepository extends JpaRepository<Votes, String> {

    @Query(value = "SELECT sum(vote_count) FROM votes WHERE thread_id =:threadID", nativeQuery = true)
    Integer threadVotes(@Param("threadID") int threadID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE votes SET vote_count = vote_count + 1 WHERE post_id = :postID", nativeQuery = true)
    void upvotePost(@Param("postID") int postID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE votes SET vote_count = vote_count - 1 WHERE post_id = :postID", nativeQuery = true)
    void downvotePost(@Param("postID") int postID);

    @Query(value = "SELECT vote_count FROM votes WHERE post_id =:postID", nativeQuery = true)
    Integer getVote(@Param("postID") int postID);

}
