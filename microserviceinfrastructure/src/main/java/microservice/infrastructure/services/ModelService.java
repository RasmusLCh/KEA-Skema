package microservice.infrastructure.services;

import microservice.infrastructure.models.ModelInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for Model services
 * */
@Service
public class ModelService {
    Map servicemap;

    @Autowired
    public ModelService(ActionService actionservice,
                        FileResourceService fileresourceservice,
                        GroupService groupservice,
                        MicroServiceService microserviceservice,
                        PageInjectionService pageinjectionservice,
                        TopMenuLinkService topmenuservice,
                        UserService userservice,
                        MicroServiceOptionService microserviceoptionservice,
                        UserMicroServiceOptionService usermicroserviceoptionservice){
        this.servicemap = new HashMap<String, CRUDServiceInterface<ModelInterface>>();
        this.servicemap.put("Action", actionservice);
        this.servicemap.put("FileResource", fileresourceservice);
        this.servicemap.put("Group", groupservice);
        this.servicemap.put("MicroService", microserviceservice);
        this.servicemap.put("PageInjection", pageinjectionservice);
        this.servicemap.put("TopMenu", topmenuservice);
        this.servicemap.put("User", userservice);
        this.servicemap.put("MicroServiceOption", microserviceoptionservice);
        this.servicemap.put("UserMicroServiceOption", usermicroserviceoptionservice);
    }

    public CRUDServiceInterface<ModelInterface> getService(String classname){
        if(servicemap.containsKey(classname)){
            return (CRUDServiceInterface<ModelInterface>)servicemap.get(classname);
        }
        return null;
    }
}
