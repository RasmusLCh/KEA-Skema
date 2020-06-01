package kea.schedule.scheduleservice.repositories;

import kea.schedule.scheduleservice.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
    List<Course> findAllByTeachersId(int id);
    List<Course> findAllByTeachersUsersId(int id);
    //@Query("SELECT DISTINCT c FROM Course c, Group g WHERE (c.teachers) or ()")
    //List<Course> findAllByTeachersUsersIdOrStudentsUsersId(int userid, int userid2);
    //List<Course> findAllByActiveIsTrueAndTeachersUsersIdOrActiveIsTrueAndStudentsUsersId(int userid, int userid2);
    List<Course> findDistinctByActiveIsTrueAndTeachersUsersIdOrActiveIsTrueAndStudentsUsersId(int userid, int userid2);
}
