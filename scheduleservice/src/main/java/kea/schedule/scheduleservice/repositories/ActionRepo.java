package kea.schedule.scheduleservice.repositories;

import kea.schedule.scheduleservice.models.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepo extends JpaRepository<Action, Integer> {
    List<Action> findAllByOrderByPriority();
    List<Action> findAllByActionnameOrderByPriority(String actionname);
}
