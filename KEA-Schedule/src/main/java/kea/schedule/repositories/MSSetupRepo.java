package kea.schedule.repositories;

import kea.schedule.modules.MicroService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MSSetupRepo extends JpaRepository<MicroService, Integer> {
    public MicroService findByName(String name);
}
