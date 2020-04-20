package kea.schedule.scheduleservice.repositories;

import kea.schedule.scheduleservice.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
    List<Course> findAllByTeachersId(int id);
    List<Course> findAllByTeachersUsersId(int id);
}
