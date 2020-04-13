package kea.schedule.scheduleservice.repositories;

import kea.schedule.scheduleservice.models.LectureSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureSubjectRepo extends JpaRepository<LectureSubject, Integer> {
}