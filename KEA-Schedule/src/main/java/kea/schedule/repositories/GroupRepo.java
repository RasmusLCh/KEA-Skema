package kea.schedule.repositories;

import kea.schedule.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GroupRepo extends JpaRepository<Group, Integer> {
    List<Group> findByMetadata(String metadata);
    Group findByName(String name);
}
