package proof.concept.modules.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proof.concept.modules.modules.PageInjection;

@Repository
public interface PageInjectionRepo extends JpaRepository<PageInjection, Integer> {
}
