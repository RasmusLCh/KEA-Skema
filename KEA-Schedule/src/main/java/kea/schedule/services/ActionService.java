package kea.schedule.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import kea.schedule.models.Action;
import kea.schedule.models.ModelInterface;
import kea.schedule.repositories.ActionRepo;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActionService implements CRUDServiceInterface<Action>{
    ActionRepo actionrepo;

    @Autowired
    public ActionService(ActionRepo actionrepo){
        this.actionrepo = actionrepo;
    }

    public void doAction(String actionname, ModelInterface model){
        ObjectMapper bla = new ObjectMapper();
        JSONObject data = bla.convertValue(model, JSONObject.class);
        doAction(actionname, data);
    }

    public void doAction(String actionname, JSONObject data){

        System.out.println("-----" + actionname + ": START -----");
        if(data == null){
            data = new JSONObject();
        }
        if(data != null){
            System.out.println(data.toJSONString());
        }
        List<Action> acl = actionrepo.findAllByActionnameAndMicroserviceEnabledIsTrue(actionname);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for(Action a: acl){
            HttpEntity<?> entity = new HttpEntity<JSONObject>(data,headers);
            System.out.println("callbackurl = " + a.getCallbackurl());
            //We dont care about the response
            restTemplate.exchange(a.getCallbackurl(), HttpMethod.POST, entity, String.class);
        }
        System.out.println("-----" + actionname + ": END -----");
    }

    @Override
    public Action create(Action action) {
        Action newaction = actionrepo.save(action);
        doAction("ActionService.create", newaction);
        return newaction;
    }

    @Override
    public void edit(Action action) {
        actionrepo.save(action);
        doAction("ActionService.edit", action);
    }

    @Override
    public void delete(int id) {
        /*
        Optional action = actionrepo.findById(id);
        if(action.isPresent()){
            actionrepo.delete((Action) action.get());
        }

         */
        actionrepo.deleteById(id);
        doAction("ActionService.delete", new Action(id));

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
