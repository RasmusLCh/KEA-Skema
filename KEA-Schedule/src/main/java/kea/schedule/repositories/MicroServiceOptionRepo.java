package kea.schedule.repositories;

import kea.schedule.modules.MicroServiceOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroServiceOptionRepo extends JpaRepository<MicroServiceOption, Integer> {
}
