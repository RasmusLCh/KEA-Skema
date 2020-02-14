package proof.concept.architecture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proof.concept.architecture.modules.FileResource;

@Repository
public interface FileResourceRepo extends JpaRepository<FileResource, Integer> {
    FileResource findByFilenameAndMicroserviceid(String filename, int microservice_id);
}
