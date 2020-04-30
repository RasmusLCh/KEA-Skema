package kea.schedule.scheduleservice.repositories;

import kea.schedule.scheduleservice.models.LectureSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureSubjectRepo extends JpaRepository<LectureSubject, Integer> {
    List<LectureSubject> findAllByLectureIdOrderByPriorityDesc(int lectureid);
    List<LectureSubject> findAllByOrderByPriorityDesc();
}