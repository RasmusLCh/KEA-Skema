package proof.concept.modules.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proof.concept.modules.modules.Action;

import java.util.List;

@Repository
public interface ActionRepo extends JpaRepository<Action, Integer> {
    public List<Action> findAllByActionname(String name);
}
