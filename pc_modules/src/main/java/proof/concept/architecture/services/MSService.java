package proof.concept.architecture.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proof.concept.architecture.modules.FileResource;
import proof.concept.architecture.modules.MicroService;
import proof.concept.architecture.repositories.ActionRepo;
import proof.concept.architecture.repositories.FileResourceRepo;
import proof.concept.architecture.repositories.PageInjectionRepo;
import proof.concept.architecture.repositories.MSSetupRepo;

@Service
public class MSService {
    MSSetupRepo msrepo;
    ActionRepo actionrepo;
    PageInjectionRepo pageinjectionrepo;
    FileResourceRepo fileresourcerepo;

    @Autowired
    public MSService(MSSetupRepo msrepo, ActionRepo actionrepo, PageInjectionRepo pageinjectionrepo, FileResourceRepo fileresourcerepo){
        this.msrepo = msrepo;
        this.actionrepo = actionrepo;
        this.pageinjectionrepo = pageinjectionrepo;
        this.fileresourcerepo = fileresourcerepo;
    }

    public MicroService findMSByName(String servicename){
        return msrepo.findByName(servicename);
    }

    public FileResource findFileResourceByMSAndFilename(String servicename, String filename){
        MicroService ms = msrepo.findByName(servicename);
        System.out.println("1 " + filename);
        if(ms != null){
            System.out.println("2 " + ms.getId());
            return fileresourcerepo.findByFilenameAndMicroserviceid(filename, ms.getId());
        }
        return null;
    }
}
