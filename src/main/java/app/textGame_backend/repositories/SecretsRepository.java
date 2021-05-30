package app.textGame_backend.repositories;

import app.textGame_backend.entities.Secrets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SecretsRepository extends JpaRepository<Secrets, String> {
    @Query(value = "SELECT * FROM secrets", nativeQuery = true)
    Secrets getCloudinarySecrets();

}
