package kea.schedule.repositories;

import kea.schedule.modules.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepo extends JpaRepository<Action, Integer> {
    public List<Action> findAllByActionname(String name);
}
