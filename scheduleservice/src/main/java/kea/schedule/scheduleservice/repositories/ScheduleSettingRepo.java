package kea.schedule.scheduleservice.repositories;

import kea.schedule.scheduleservice.models.ScheduleSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleSettingRepo extends JpaRepository<ScheduleSetting, Integer> {
    ScheduleSetting findByName(String name);
}
