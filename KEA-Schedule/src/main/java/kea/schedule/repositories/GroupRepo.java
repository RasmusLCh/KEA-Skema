package kea.schedule.repositories;

import kea.schedule.moduls.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupRepo extends JpaRepository<Group, Integer> {
}
