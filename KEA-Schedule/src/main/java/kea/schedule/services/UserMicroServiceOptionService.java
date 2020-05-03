package kea.schedule.services;

import kea.schedule.models.UserMicroServiceOption;
import kea.schedule.repositories.UserMicroServiceOptionRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMicroServiceOptionService implements CRUDServiceInterface<UserMicroServiceOption> {
    private UserMicroServiceOptionRepo repo;
    private AuthenticationService authenticationservice;
    private ActionService actionservice;
    public UserMicroServiceOptionService(UserMicroServiceOptionRepo repo, AuthenticationService authenticationservice, ActionService actionservice)
    {
        this.repo = repo;
        this.authenticationservice = authenticationservice;
        this.actionservice = actionservice;
    }

    @Override
    public UserMicroServiceOption create(UserMicroServiceOption userMicroServiceOption) {
        return null;
    }

    @Override
    public void edit(UserMicroServiceOption userMicroServiceOption) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public UserMicroServiceOption findById(int id) {
        return null;
    }

    @Override
    public List<UserMicroServiceOption> findAll() {
        return null;
    }

    @Override
    public List<UserMicroServiceOption> findAll(int microserviceid) {
        return null;
    }

    public UserMicroServiceOption findByName(String name) {
        //User user = authenticationservice.;
        return null;
    }
}
