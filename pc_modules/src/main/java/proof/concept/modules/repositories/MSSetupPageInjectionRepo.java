package proof.concept.modules.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proof.concept.modules.modules.MicroServicePageInjection;

@Repository
public interface MSSetupPageInjectionRepo extends JpaRepository<MicroServicePageInjection, Integer> {
}
