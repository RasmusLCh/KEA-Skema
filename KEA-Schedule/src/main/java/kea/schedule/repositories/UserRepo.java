package kea.schedule.repositories;

import kea.schedule.moduls.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
