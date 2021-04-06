package app.textGame_backend.repositories;

import app.textGame_backend.entities.Threads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ThreadRepository extends JpaRepository<Threads, String> {
    /*@Query(value = "SELECT * \n" +
            "FROM thread \n" +
            "WHERE thread.created > DATE_SUB(CURDATE(), INTERVAL 1 DAY) \n" +
            "ORDER BY created DESC", nativeQuery = true)
     */
    @Query(value = "SELECT * \n" +
            "FROM threads \n" +
            "ORDER BY created DESC", nativeQuery = true)
    ArrayList<Threads> getNewestThread();

    @Query(value = "SELECT * FROM threads WHERE id = :threadID", nativeQuery = true)
    Threads getThreadWithId(@Param("threadID") int threadID);

    @Query(value = "SELECT * FROM threads WHERE id_user = :id_user", nativeQuery = true)
    ArrayList<Threads> getThreadsCreatedByUser(@Param("id_user") int id_user);

    @Query(value = "SELECT id, thread_title, id_user, imageURL, content, created, status FROM threads INNER JOIN \n" +
            "(SELECT SUM(vote_count) AS votes, thread_id FROM votes GROUP BY thread_id) t2 ON t2.thread_id = thread.id  \n" +
            "ORDER BY t2.votes DESC", nativeQuery = true)
    ArrayList<Threads> getMostPopThread();
}
