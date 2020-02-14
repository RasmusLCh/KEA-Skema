package proof.concept.architecture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proof.concept.architecture.modules.Action;

import java.util.List;

@Repository
public interface ActionRepo extends JpaRepository<Action, Integer> {
    public List<Action> findAllByActionname(String name);
}
