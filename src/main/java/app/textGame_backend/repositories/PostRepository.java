package app.textGame_backend.repositories;

import app.textGame_backend.entities.Category;
import app.textGame_backend.entities.Post;
import app.textGame_backend.entities.Threads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface PostRepository extends JpaRepository<Post, String> {
    @Query(value = "SELECT * \n" +
            "FROM post \n" +
            "WHERE thread_id = :thread_id \n" +
            "ORDER BY created DESC \n" +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    ArrayList<Post> getComments(@Param("thread_id") int thread_id, @Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT * FROM post WHERE id_user = :id_user", nativeQuery = true)
    ArrayList<Post> getPostsCreatedByUserNoLimit(@Param("id_user") int id_user);

    @Query(value = "SELECT * FROM post WHERE id_user = :id_user LIMIT :limit OFFSET :offset", nativeQuery = true)
    ArrayList<Post> getPostsCreatedByUser(@Param("id_user") int id_user, @Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT COUNT(*) \n" +
            "FROM post \n" +
            "WHERE thread_id = :thread_id \n" +
            "ORDER BY created DESC \n", nativeQuery = true)
    Integer getNumberOfCommentsInThread(@Param("thread_id") int thread_id);

    @Query(value = "SELECT post.id, post.id_user, post.thread_id, post_title, content, imageURL, created, status FROM post left JOIN\n" +
            "(SELECT * FROM votes) t2 on t2.post_id = post.id where post.thread_id = :thread_id \n" +
            "ORDER BY vote_count DESC \n" +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    ArrayList<Post> getCommentsByVotes(@Param("thread_id") int thread_id, @Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT * FROM post WHERE id = :post_id", nativeQuery = true)
    Post getPostById(@Param("post_id") int post_id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM post WHERE id = :post_id", nativeQuery = true)
    void deletePostById(@Param("post_id") int post_id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM post WHERE id_user = :id_user", nativeQuery = true)
    void deletePostByUserId(@Param("id_user") int id_user);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM post WHERE thread_id = :thread_id", nativeQuery = true)
    void deletePostByThreadId(@Param("thread_id") int thread_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE post SET content = :content WHERE id = :postID", nativeQuery = true)
    void editComment(@Param("content") String content, @Param("postID") int userID);
}
