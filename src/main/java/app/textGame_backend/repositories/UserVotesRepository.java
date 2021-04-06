package app.textGame_backend.repositories;

import app.textGame_backend.entities.UserVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserVotesRepository extends JpaRepository<UserVotes, String> {

    @Query(value = "SELECT vote FROM user_votes WHERE user_id =:user_id and post_id =:post_id", nativeQuery = true)
    Integer existingUserVote(@Param("user_id") int user_id, @Param("post_id") int post_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_votes SET vote = vote + 1 WHERE user_id =:user_id and post_id =:post_id", nativeQuery = true)
    void addCurrentUserVote (@Param("user_id") int user_id, @Param("post_id") int post_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_votes SET vote = vote - 1 WHERE user_id =:user_id and post_id =:post_id", nativeQuery = true)
    void minusCurrentUserVote (@Param("user_id") int user_id, @Param("post_id") int post_id);
}