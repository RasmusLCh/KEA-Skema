package kea.schedule.services;

import kea.schedule.models.FileResource;
import kea.schedule.models.MicroService;
import kea.schedule.repositories.ActionRepo;
import kea.schedule.repositories.FileResourceRepo;
import kea.schedule.repositories.MicroServiceRepo;
import kea.schedule.repositories.PageInjectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MicroServiceService implements CRUDServiceInterface<MicroService> {
    private MicroServiceRepo msrepo;
    private ActionRepo actionrepo;
    private PageInjectionRepo pageinjectionrepo;
    private FileResourceRepo fileresourcerepo;
    private ActionService actionservice;

    @Autowired
    public MicroServiceService(MicroServiceRepo msrepo, ActionRepo actionrepo, PageInjectionRepo pageinjectionrepo, FileResourceRepo fileresourcerepo, ActionService actionservice){
        this.msrepo = msrepo;
        this.actionrepo = actionrepo;
        this.pageinjectionrepo = pageinjectionrepo;
        this.fileresourcerepo = fileresourcerepo;
        this.actionservice = actionservice;
    }

    /**
     * Default behavious is that disabled MicroServices arent visible by name
     * */
    public MicroService findByName(String servicename){
        return findByName(servicename, false);
    }

    /**
     * Default behavious is that disabled MicroServices arent visible by name, you can choose to set includedisabled to true, to be able to get disabled microservices
     * */
    public MicroService findByName(String servicename, boolean includedisabled){
        if(includedisabled){
            return msrepo.findByName(servicename);
        }
        return msrepo.findByNameAndEnabledIsTrue(servicename);
    }

    public FileResource findFileResourceByMSAndFilename(String servicename, String filename){
        MicroService ms = msrepo.findByName(servicename);
        System.out.println("1 " + filename);
        if(ms != null){
            System.out.println("2 " + ms.getId());
            return fileresourcerepo.findByFilenameAndMicroserviceIdAndMicroserviceEnabledIsTrue(filename, ms.getId());
        }
        return null;
    }

    public List<MicroService> findAll(){
        List<MicroService> services = msrepo.findAll();
        if(services == null){
            services = new ArrayList<>();
        }
        return services;
    }

    @Override
    public MicroService create(MicroService microService) {
        MicroService newms = msrepo.save(microService);
        actionservice.doAction("MicroServiceService.create", newms);
        return newms;
    }

    @Override
    public void edit(MicroService microservice) {
        msrepo.save(microservice);
        actionservice.doAction("MicroServiceService.create", microservice);
    }

    @Override
    public void delete(int id) {
        /*
        Optional ms = msrepo.findById(id);
        if(ms.isPresent()){
            msrepo.delete((MicroService) ms.get());
        }

         */
        msrepo.deleteById(id);
        actionservice.doAction("MicroServiceService.delete", new MicroService(id));

    }

    public MicroService findById(int id){
        Optional optms = msrepo.findById(id);
        if(optms.isPresent()){
            return (MicroService) optms.get();
        }
        return null;
    }
}
