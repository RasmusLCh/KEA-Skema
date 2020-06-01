package kea.schedule.scheduleservice.repositories;

import kea.schedule.scheduleservice.models.LectureSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LectureSubjectRepo extends JpaRepository<LectureSubject, Integer> {
    List<LectureSubject> findAllByLectureIdOrderByPriorityDesc(int lectureid);
    List<LectureSubject> findAllByOrderByPriorityDesc();
    @Transactional
    @Modifying
    @Query("UPDATE LectureSubject ls SET ls.priority = :priority WHERE ls.subject = :subject")
    void updatePrioBasedOnSubjectPriority(@Param("subject") String subject, @Param("priority") int priority);
}