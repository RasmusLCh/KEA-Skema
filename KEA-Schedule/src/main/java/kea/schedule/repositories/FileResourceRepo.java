package kea.schedule.repositories;

import kea.schedule.moduls.FileResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileResourceRepo extends JpaRepository<FileResource, Integer> {
    FileResource findByFilenameAndMicroserviceId(String filename, int microservice_id);
}
