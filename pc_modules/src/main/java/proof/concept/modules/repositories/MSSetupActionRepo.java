package proof.concept.modules.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proof.concept.modules.modules.MicroServiceAction;

@Repository
public interface MSSetupActionRepo extends JpaRepository<MicroServiceAction, Integer> {
}
