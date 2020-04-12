package kea.schedule.repositories;

import kea.schedule.models.FileResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileResourceRepo extends JpaRepository<FileResource, Integer> {
    //FileResource findByFilenameAndMicroserviceId(String filename, int microservice_id);
    FileResource findByFilenameAndMicroserviceIdAndMicroserviceEnabledIsTrue(String filename, int microservice_id);
}
