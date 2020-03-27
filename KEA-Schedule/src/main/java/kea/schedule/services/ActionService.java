package kea.schedule.services;

import kea.schedule.moduls.Action;
import kea.schedule.repositories.ActionRepo;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActionService implements CRUDServiceInterface<Action>{
    ActionRepo actionrepo;

    @Autowired
    public ActionService(ActionRepo actionrepo){
        this.actionrepo = actionrepo;
    }

    public void doAction(String actionname, JSONObject data){
        if(data == null){
            data = new JSONObject();
            //TEST DATA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            data.appendField("My field", "My value");
        }
        List<Action> acl = actionrepo.findAllByActionname(actionname);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for(Action a: acl){
            HttpEntity<?> entity = new HttpEntity<JSONObject>(data,headers);
            System.out.println("callbackurl = " + a.getCallbackurl());
            //We dont care about the response
            restTemplate.exchange(a.getCallbackurl(), HttpMethod.POST, entity, String.class);
        }
    }

    @Override
    public Action create(Action action) {
        return actionrepo.save(action);
    }

    @Override
    public void edit(Action action) {
        actionrepo.save(action);
    }

    @Override
    public void delete(int id) {
        Optional action = actionrepo.findById(id);
        if(action.isPresent()){
            actionrepo.delete((Action) action.get());
        }

    }

    @Override
    public Action findById(int id) {
        Optional action = actionrepo.findById(id);
        if(action.isPresent()){
            return (Action) action.get();
        }
        return null;
    }

    @Override
    public List<Action> findAll() {
        return actionrepo.findAll();
    }
}
