package proof.concept.architecture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proof.concept.architecture.modules.MicroService;

@Repository
public interface MSSetupRepo extends JpaRepository<MicroService, Integer> {
    public MicroService findByName(String name);
}
