package microservice.infrastructure.services;

import microservice.infrastructure.models.MicroServiceOption;
import microservice.infrastructure.models.User;
import microservice.infrastructure.models.UserMicroServiceOption;
import microservice.infrastructure.repositories.UserMicroServiceOptionRepo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * CRUD for UserMicroServiceOption
 * */

@Service
public class UserMicroServiceOptionService implements CRUDServiceInterface<UserMicroServiceOption> {
    private UserMicroServiceOptionRepo repo;
    private AuthenticationService authenticationservice;
    private ActionService actionservice;
    private MicroServiceOptionService microserviceoptionservice;

    public UserMicroServiceOptionService(UserMicroServiceOptionRepo repo, AuthenticationService authenticationservice, ActionService actionservice, MicroServiceOptionService microserviceoptionservice)
    {
        this.repo = repo;
        this.authenticationservice = authenticationservice;
        this.actionservice = actionservice;
        this.microserviceoptionservice = microserviceoptionservice;
    }

    @Override
    public UserMicroServiceOption create(UserMicroServiceOption userMicroServiceOption) {
        UserMicroServiceOption newtml = repo.save(userMicroServiceOption);
        actionservice.doAction("TopMenuLinkService.create", userMicroServiceOption);
        return newtml;
    }

    @Override
    public void edit(UserMicroServiceOption userMicroServiceOption) {
        //We cant gurantie that we got the ID set, so we make a lookup prior to saving
        UserMicroServiceOption umso = repo.findByUserIdAndMicroserviceoptionId(userMicroServiceOption.getUser().getId(), userMicroServiceOption.getMicroserviceoption().getId());
        if(umso != null){
            userMicroServiceOption.setId(umso.getId());
        }
        repo.save(userMicroServiceOption);
        actionservice.doAction("TopMenuLinkService.edit", userMicroServiceOption);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
        actionservice.doAction("TopMenuLinkService.delete", new UserMicroServiceOption(id));
    }

    @Override
    public UserMicroServiceOption findById(int id) {
        Optional tml = repo.findById(id);
        if(tml.isPresent()){
            return (UserMicroServiceOption) tml.get();
        }
        return null;
    }

    @Override
    public List<UserMicroServiceOption> findAll() {
        return repo.findAll();
    }

    @Override
    public List<UserMicroServiceOption> findAll(int microserviceid) {
        return repo.findAllByMicroserviceoptionMicroserviceId(microserviceid);
    }

    public UserMicroServiceOption findByNameAndUser(String name, User user) {
        UserMicroServiceOption umso = repo.findByMicroserviceoptionNameAndUserId(name, user.getId());
        if(umso == null){
            MicroServiceOption mso = microserviceoptionservice.findByName(name);
            if(mso != null){
                return create(repo.save(new UserMicroServiceOption(mso, user)));
            }
        }
        else{
            return umso;
        }
        return null;
    }

    public Map<String, UserMicroServiceOption> getUserMicroServiceOptions(User user){
        Map<String, UserMicroServiceOption> mopts = new HashMap();
        List<MicroServiceOption> msos = microserviceoptionservice.findAll();
        for(MicroServiceOption mso : msos){
            mopts.put(mso.getName(), new UserMicroServiceOption(mso, user));
        }
        //If the user has specific values, we overwritte the default values we just loaded
        List<UserMicroServiceOption> opts = repo.findByUserId(user.getId());
        for(UserMicroServiceOption opt : opts){
            mopts.put(opt.getMicroserviceoption().getName(), opt);
        }
        return mopts;
    }
}
