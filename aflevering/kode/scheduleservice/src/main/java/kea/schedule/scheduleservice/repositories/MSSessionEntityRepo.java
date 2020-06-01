package kea.schedule.scheduleservice.repositories;

import kea.schedule.scheduleservice.models.MSSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MSSessionEntityRepo extends JpaRepository<MSSessionEntity, Integer> {
    MSSessionEntity findByUserid(int userid);
}