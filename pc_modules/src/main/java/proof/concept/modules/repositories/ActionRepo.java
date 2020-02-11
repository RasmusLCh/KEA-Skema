package proof.concept.modules.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proof.concept.modules.modules.Action;

@Repository
public interface ActionRepo extends JpaRepository<Action, Integer> {
}
