package kea.schedule.scheduleservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import kea.schedule.scheduleservice.models.Action;
import kea.schedule.scheduleservice.models.ModelInterface;
import kea.schedule.scheduleservice.repositories.ActionRepo;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Handles CRUD for actions and handles when actions are triggered, to alert any microservice listening for the specific action.
 * */

@Service
public class ActionService implements CRUDServiceInterface<Action>{
    ActionRepo actionrepo;

    @Autowired
    public ActionService(ActionRepo actionrepo){
        this.actionrepo = actionrepo;
    }

    /**
     * @Param   actionname The name of the action
     * @Param   model   Data associated with the action
     * */
    public void doAction(String actionname, ModelInterface model){
        ObjectMapper bla = new ObjectMapper();
        System.out.println("doAction 1");
        JSONObject data = bla.convertValue(model, JSONObject.class);
        System.out.println("doAction 2");
        doAction(actionname, data);
    }

    /**
     * Any microservice listening for the action call, will get alerted with the data.
     *
     * @Param   actionname The name of the action
     * @Param   data   Data associated with the action
     * */
    public void doAction(String actionname, JSONObject data){

        System.out.println("-----" + actionname + ": START -----");
        if(data == null){
            data = new JSONObject();
        }
        if(data != null){
            System.out.println(data.toJSONString());
        }
        List<Action> acl = actionrepo.findAllByActionnameOrderByPriority(actionname);
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
