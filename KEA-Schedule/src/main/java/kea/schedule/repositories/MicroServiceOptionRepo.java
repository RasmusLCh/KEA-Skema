package kea.schedule.repositories;

import kea.schedule.models.MicroService;
import kea.schedule.models.MicroServiceOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MicroServiceOptionRepo extends JpaRepository<MicroServiceOption, Integer> {
    List<MicroServiceOption> findAllByMicroserviceId(int msid);
    MicroServiceOption findByName(String name);
}
