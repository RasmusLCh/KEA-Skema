package kea.schedule.scheduleservice.repositories;

import kea.schedule.scheduleservice.models.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepo extends JpaRepository<Lecture, Integer> {
    List<Lecture> findAllByCourseId(int courseid);
}