package microservice.infrastructure.repositories;

import microservice.infrastructure.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByIdentifier(String identifer);
    User findByEmail(String email);
}
