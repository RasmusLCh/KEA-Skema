package kea.schedule.scheduleservice.services;

import kea.schedule.scheduleservice.components.MSSession;
import kea.schedule.scheduleservice.models.User;
import kea.schedule.scheduleservice.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * CRUD for User
 * */

@Service
public class UserService implements CRUDServiceInterface<User> {
    private UserRepo userrepo;
    private ActionService actionservice;
    private MSSession session;

    @Autowired
    public UserService(UserRepo userrepo, ActionService actionservice, MSSession session){
        this.userrepo = userrepo;
        this.actionservice = actionservice;
        this.session = session;
    }

    @Override
    public User create(User user) {
        User newuser = userrepo.save(user);
        actionservice.doAction("UserService.create", newuser);
        return newuser;
    }

    @Override
    public void edit(User user) {
        userrepo.save(user);
        actionservice.doAction("UserService.edit", user);
    }

    @Override
    public void delete(int id) {
        userrepo.deleteById(id);
        actionservice.doAction("UserService.delete", new User(id));
    }

    @Override
    public User findById(int id) {
        Optional optuser = userrepo.findById(id);
        if(optuser.isPresent()){
            return (User)optuser.get();
        }
        return null;
    }

    public User findByDisplayname(String displayname){
        return userrepo.findByDisplayname(displayname);
    }

    public User findByIdentifier(String identifier){
        return userrepo.findByIdentifier(identifier);
    }

    public User findByEmail(String email){ return userrepo.findByEmail(email); }

    @Override
    public List<User> findAll() {
        return userrepo.findAll();
    }

    public int getCurrentUserId(){
        return session.getUserId();
    }

    public User getCurrentUser(){
        return findById(session.getUserId());
    }
}
