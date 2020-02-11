package proof.concept.modules.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proof.concept.modules.modules.MicroService;

@Repository
public interface MSSetupRepo extends JpaRepository<MicroService, Integer> {
    public MicroService findByName(String name);
}
