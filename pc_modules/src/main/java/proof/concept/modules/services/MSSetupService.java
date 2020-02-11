package proof.concept.modules.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proof.concept.modules.modules.MicroService;
import proof.concept.modules.modules.Action;
import proof.concept.modules.modules.PageInjection;
import proof.concept.modules.repositories.ActionRepo;
import proof.concept.modules.repositories.PageInjectionRepo;
import proof.concept.modules.repositories.MSSetupRepo;

import java.util.List;

@Service
public class MSSetupService {

    MSSetupRepo msrepo;
    ActionRepo actionrepo;
    PageInjectionRepo pageinjectionrepo;

    @Autowired
    public MSSetupService(MSSetupRepo msrepo, ActionRepo actionrepo, PageInjectionRepo pageinjectionrepo){
        this.msrepo = msrepo;
        this.actionrepo = actionrepo;
        this.pageinjectionrepo = pageinjectionrepo;
    }

    public MicroService serviceregistration(MicroService service){
        return msrepo.save(service);
    }

    public void serviceremoval(MicroService service){
        if(service.getId() == -1 || service.getId() == 0){
            System.out.println(service.getName());
            service = msrepo.findByName(service.getName());
        }
        if(service != null && service.getId() > 0){
            msrepo.delete(service);
        }
    }

    public void serviceaddpageinjection(PageInjection pi){
        pageinjectionrepo.save(pi);
    }

    public void serviceaddaction(Action msa){
        actionrepo.save(msa);
    }

    public List<MicroService> getAllMS(){
        return msrepo.findAll();
    }
}
