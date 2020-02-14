package proof.concept.architecture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proof.concept.architecture.modules.PageInjection;

import java.util.List;

@Repository
public interface PageInjectionRepo extends JpaRepository<PageInjection, Integer> {
    public List<PageInjection> findByPageAndType(String page, String type);
}
