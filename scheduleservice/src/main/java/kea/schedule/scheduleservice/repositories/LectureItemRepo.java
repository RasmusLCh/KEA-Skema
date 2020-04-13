package kea.schedule.scheduleservice.repositories;

import kea.schedule.scheduleservice.models.LectureItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureItemRepo extends JpaRepository<LectureItem, Integer> {
}