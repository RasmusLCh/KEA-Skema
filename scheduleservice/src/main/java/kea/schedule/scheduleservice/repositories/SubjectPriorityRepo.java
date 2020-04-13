package kea.schedule.scheduleservice.repositories;

import kea.schedule.scheduleservice.models.SubjectPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectPriorityRepo extends JpaRepository<SubjectPriority, Integer> {
    SubjectPriority findBySubject(String subject);
}