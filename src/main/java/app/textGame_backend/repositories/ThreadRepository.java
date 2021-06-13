package app.textGame_backend.repositories;

import app.textGame_backend.entities.Threads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface ThreadRepository extends JpaRepository<Threads, String> {
    /*@Query(value = "SELECT * \n" +
            "FROM thread \n" +
            "WHERE thread.created > DATE_SUB(CURDATE(), INTERVAL 1 DAY) \n" +
            "ORDER BY created DESC", nativeQuery = true)
     */
    @Query(value = "SELECT * \n" +
            "FROM threads \n" +
            "ORDER BY created DESC \n" +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    ArrayList<Threads> getNewestThread(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT * FROM threads WHERE id = :threadID", nativeQuery = true)
    Threads getThreadWithId(@Param("threadID") int threadID);

    @Query(value = "SELECT * FROM threads WHERE id_user = :id_user LIMIT :limit OFFSET :offset", nativeQuery = true)
    ArrayList<Threads> getThreadsCreatedByUser(@Param("id_user") int id_user, @Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT * FROM threads WHERE id_user = :id_user", nativeQuery = true)
    ArrayList<Threads> getThreadsCreatedByUserNoLimit(@Param("id_user") int id_user);

    @Query(value = "SELECT id, thread_title, id_user, imageURL, content, created, status FROM threads left JOIN \n" +
            "(SELECT SUM(vote_count) AS votes, thread_id FROM votes GROUP BY thread_id) t2 ON t2.thread_id = threads.id  \n" +
            "ORDER BY t2.votes DESC \n" +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    ArrayList<Threads> getMostPopThread(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT id, thread_title, id_user, imageURL, content, created, status FROM threads INNER JOIN \n" +
            "(SELECT SUM(vote_count) AS votes, thread_id FROM votes GROUP BY thread_id) t2 ON t2.thread_id = threads.id  \n" +
            "where t2.votes >= 10  \n" +
            "ORDER BY threads.created DESC \n" +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    ArrayList<Threads> getRecommendedThread(@Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT * FROM threads inner join\n" +
            "(SELECT thread_id FROM post WHERE id_user = :id_user) t2 ON t2.thread_id = threads.id\n" +
            "GROUP BY threads.id \n" +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    ArrayList<Threads> getAllThreadsWithCommentsByUser(@Param("id_user") int id_user, @Param("limit") int limit, @Param("offset") int offset);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM threads WHERE id_user = :id_user", nativeQuery = true)
    void deleteThreadByUserId(@Param("id_user") int id_user);
}
