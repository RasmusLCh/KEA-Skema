package kea.schedule.repositories;

import kea.schedule.modules.TopMenuLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopMenuLinkRepo extends JpaRepository<TopMenuLink, Integer> {
}
