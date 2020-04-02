package kea.schedule.services;

import kea.schedule.moduls.User;
import kea.schedule.repositories.UserRepo;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements CRUDServiceInterface<User> {
    private UserRepo userrepo;
    private ActionService actionservice;

    @Autowired
    public UserService(UserRepo userrepo, ActionService actionservice){
        this.userrepo = userrepo;
        this.actionservice = actionservice;
    }

    @Override
    public User create(User user) {
        User newuser = userrepo.save(user);
        actionservice.doAction("UserService.create", user.toJSON(new JSONObject()));
        return newuser;
    }

    @Override
    public void edit(User user) {
        userrepo.save(user);
        actionservice.doAction("UserService.edit", user.toJSON(new JSONObject()));
    }

    @Override
    public void delete(int id) {
        userrepo.deleteById(id);
        actionservice.doAction("UserService.delete", new JSONObject().appendField("id", id));
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

    public User findByEmail(String email){ return userrepo.findByEmail(email); }

    @Override
    public List<User> findAll() {
        return userrepo.findAll();
    }


}
