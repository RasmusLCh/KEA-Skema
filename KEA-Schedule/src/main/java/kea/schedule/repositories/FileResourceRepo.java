package kea.schedule.repositories;

import kea.schedule.modules.FileResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileResourceRepo extends JpaRepository<FileResource, Integer> {
    FileResource findByFilenameAndMicroserviceid(String filename, int microservice_id);
}
