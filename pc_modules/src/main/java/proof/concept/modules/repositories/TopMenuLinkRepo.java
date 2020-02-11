package proof.concept.modules.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proof.concept.modules.modules.TopMenuLink;

@Repository
public interface TopMenuLinkRepo extends JpaRepository<TopMenuLink, Integer> {
}
