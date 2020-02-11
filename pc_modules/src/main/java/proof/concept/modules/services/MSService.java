package proof.concept.modules.services;

import org.springframework.stereotype.Service;
import proof.concept.modules.modules.MicroService;
import proof.concept.modules.repositories.ActionRepo;
import proof.concept.modules.repositories.PageInjectionRepo;
import proof.concept.modules.repositories.MSSetupRepo;

@Service
public class MSService {
    MSSetupRepo msrepo;
    ActionRepo actionrepo;
    PageInjectionRepo pageinjectionrepo;

    public MSService(MSSetupRepo msrepo, ActionRepo actionrepo, PageInjectionRepo pageinjectionrepo){
        this.msrepo = msrepo;
        this.actionrepo = actionrepo;
        this.pageinjectionrepo = pageinjectionrepo;
    }

    public MicroService findMSByName(String servicename){
        return msrepo.findByName(servicename);
    }
}
