package kea.schedule.services;

import kea.schedule.modules.FileResource;
import kea.schedule.modules.MicroService;
import kea.schedule.repositories.ActionRepo;
import kea.schedule.repositories.FileResourceRepo;
import kea.schedule.repositories.MSSetupRepo;
import kea.schedule.repositories.PageInjectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
