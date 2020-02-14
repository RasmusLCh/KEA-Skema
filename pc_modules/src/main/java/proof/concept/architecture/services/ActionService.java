package proof.concept.architecture.services;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import proof.concept.architecture.modules.Action;

import org.springframework.stereotype.Service;
import proof.concept.architecture.repositories.ActionRepo;

import java.util.List;

@Service
public class ActionService {
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

    public void addAction(Action action){
        actionrepo.save(action);
    }
}
