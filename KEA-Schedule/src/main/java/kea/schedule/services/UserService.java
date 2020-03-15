package kea.schedule.services;

import kea.schedule.moduls.User;
import kea.schedule.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements CRUDServiceInterface<User> {
    UserRepo userrepo;
    @Autowired
    public UserService(UserRepo userrepo){
        this.userrepo = userrepo;
    }

    @Override
    public User create(User user) {
        return userrepo.save(user);
    }

    @Override
    public void edit(User user) {
        userrepo.save(user);
    }

    @Override
    public void delete(int id) {
        userrepo.deleteById(id);
    }

    @Override
    public User findById(int id) {
        Optional optuser = userrepo.findById(id);
        if(optuser.isPresent()){
            return (User)optuser.get();
        }
        return null;
    }

    public User findByIdentifier(String identifier){
        return userrepo.findByIdentifier(identifier);
    }

    @Override
    public List<User> findAll() {
        return userrepo.findAll();
    }
}
