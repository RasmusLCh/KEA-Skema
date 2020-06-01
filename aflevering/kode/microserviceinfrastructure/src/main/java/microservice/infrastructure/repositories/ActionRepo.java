package microservice.infrastructure.repositories;

import microservice.infrastructure.models.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepo extends JpaRepository<Action, Integer> {
    //List<Action> findAllByActionname(String name);
    List<Action> findAllByActionnameAndMicroserviceEnabledIsTrue(String name);
    List<Action> findAllByMicroserviceId(int msid);
}
