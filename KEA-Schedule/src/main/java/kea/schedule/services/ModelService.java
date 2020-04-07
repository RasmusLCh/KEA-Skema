package kea.schedule.services;

import kea.schedule.moduls.FileResource;
import kea.schedule.moduls.ModelInterface;
import kea.schedule.moduls.PageInjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
                        UserService userservice){
        this.servicemap = new HashMap<String, CRUDServiceInterface<ModelInterface>>();
        this.servicemap.put("Action", actionservice);
        this.servicemap.put("FileResource", fileresourceservice);
        this.servicemap.put("Group", groupservice);
        this.servicemap.put("MicroServiceService", microserviceservice);
        this.servicemap.put("PageInjection", pageinjectionservice);
        this.servicemap.put("TopMenu", topmenuservice);
        this.servicemap.put("User", userservice);
    }

    public CRUDServiceInterface<ModelInterface> getService(String classname){
        if(servicemap.containsKey(classname)){
            return (CRUDServiceInterface<ModelInterface>)servicemap.get(classname);
        }
        return null;
    }
}
