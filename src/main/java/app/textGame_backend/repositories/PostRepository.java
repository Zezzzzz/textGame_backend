package app.textGame_backend.repositories;

import app.textGame_backend.entities.Post;
import app.textGame_backend.entities.Threads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface PostRepository extends JpaRepository<Post, String> {
    @Query(value = "SELECT * \n" +
            "FROM post \n" +
            "WHERE thread_id = :thread_id \n" +
            "ORDER BY created DESC", nativeQuery = true)
    ArrayList<Post> getComments(@Param("thread_id") int thread_id);

    @Query(value = "SELECT * FROM post WHERE id_user = :id_user", nativeQuery = true)
    ArrayList<Post> getPostsCreatedByUser(@Param("id_user") int id_user);
}
