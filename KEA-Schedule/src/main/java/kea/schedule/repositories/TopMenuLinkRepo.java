package kea.schedule.repositories;

import kea.schedule.models.MicroService;
import kea.schedule.models.TopMenuLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopMenuLinkRepo extends JpaRepository<TopMenuLink, Integer> {
//    List<TopMenuLink> findAllByLanguageOrderByPriority(String language);
    List<TopMenuLink> findAllByLanguageAndMicroserviceEnabledIsTrueOrderByPriority(String language);
    List<TopMenuLink> findAllByMicroserviceIdOrderByPriority(int msid);
}
