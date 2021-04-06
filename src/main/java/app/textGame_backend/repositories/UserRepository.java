package app.textGame_backend.repositories;

import app.textGame_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM user WHERE username = :username", nativeQuery = true)
    User checkUserExistenceWithUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM user WHERE id_token = :id_token AND email = :email", nativeQuery = true)
    User checkUserExistenceWithID(@Param("id_token") String id_token, @Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user WHERE user_id = :userID", nativeQuery = true)
    void deleteUser(@Param("userID") int userID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET username = :username WHERE id = :userID", nativeQuery = true)
    void updateUserPassword(@Param("username") String username, @Param("userID") int userID);

    @Query(value = "SELECT * FROM user WHERE id = (SELECT id_user FROM threads WHERE id = :threadID)", nativeQuery = true)
    User getCreatorOfThread(@Param("threadID") int threadID);

    @Query(value = "SELECT * FROM user WHERE id = (SELECT id_user FROM post WHERE id = :postID)", nativeQuery = true)
    User getCreatorOfPost(@Param("postID") int postID);

}
