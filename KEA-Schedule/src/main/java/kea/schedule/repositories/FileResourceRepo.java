package kea.schedule.repositories;

import kea.schedule.models.FileResource;
import kea.schedule.models.MicroService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FileResourceRepo extends JpaRepository<FileResource, Integer> {
    //FileResource findByFilenameAndMicroserviceId(String filename, int microservice_id);
    FileResource findByFilenameAndMicroserviceIdAndMicroserviceEnabledIsTrue(String filename, int microservice_id);
    List<FileResource> findAllByMicroserviceId(int msid);
}
