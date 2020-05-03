package kea.schedule.repositories;

import kea.schedule.models.UserMicroServiceOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMicroServiceOptionRepo extends JpaRepository<UserMicroServiceOption, Integer> {
    //List<UserMicroServiceOption> findByName(String name);
}
