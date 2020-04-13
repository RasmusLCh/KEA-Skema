package kea.schedule.repositories;

import kea.schedule.models.MicroService;
import kea.schedule.models.PageInjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageInjectionRepo extends JpaRepository<PageInjection, Integer> {
    //List<PageInjection> findByPageAndType(String page, String type);
    List<PageInjection> findByPageAndTypeAndMicroserviceEnabledIsTrue(String page, String type);
    List<PageInjection> findAllByMicroserviceId(int msid);
}
