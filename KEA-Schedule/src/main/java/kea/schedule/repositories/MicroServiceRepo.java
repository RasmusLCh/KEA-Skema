package kea.schedule.repositories;

import kea.schedule.models.MicroService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroServiceRepo extends JpaRepository<MicroService, Integer> {
    MicroService findByName(String name);
    MicroService findByNameAndEnabledIsTrue(String name);
}
