package proof.concept.architecture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proof.concept.architecture.modules.TopMenuLink;

@Repository
public interface TopMenuLinkRepo extends JpaRepository<TopMenuLink, Integer> {
}
