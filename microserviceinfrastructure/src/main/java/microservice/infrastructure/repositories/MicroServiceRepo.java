package microservice.infrastructure.repositories;

import microservice.infrastructure.models.MicroService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroServiceRepo extends JpaRepository<MicroService, Integer> {
    MicroService findByName(String name);
    MicroService findByNameAndEnabledIsTrue(String name);
}
