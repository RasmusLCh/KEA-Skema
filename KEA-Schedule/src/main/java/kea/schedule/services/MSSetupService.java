package kea.schedule.services;

import kea.schedule.modules.*;
import kea.schedule.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MSSetupService {

    private MicroServiceRepo msrepo;
    private ActionRepo actionrepo;
    private PageInjectionRepo pageinjectionrepo;
    private TopMenuLinkRepo topmenulinkrepo;
    private FileResourceRepo fileresourcerepo;
    private MicroServiceOptionRepo microserviceoptionrepo;

    @Autowired
    public MSSetupService(MicroServiceRepo msrepo,
                          ActionRepo actionrepo,
                          PageInjectionRepo pageinjectionrepo,
                          TopMenuLinkRepo topmenulinkrepo,
                          FileResourceRepo fileresourcerepo,
                          MicroServiceOptionRepo microserviceoptionrepo){
        this.msrepo = msrepo;
        this.actionrepo = actionrepo;
        this.pageinjectionrepo = pageinjectionrepo;
        this.topmenulinkrepo = topmenulinkrepo;
        this.fileresourcerepo = fileresourcerepo;
        this.microserviceoptionrepo = microserviceoptionrepo;
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

    public void serviceaddtopmenulink(TopMenuLink link){
        topmenulinkrepo.save(link);
    }

    public void serviceaddmicroserviceoption(MicroServiceOption microserviceoption){
        microserviceoptionrepo.save(microserviceoption);
    }

    public List<MicroService> getAllMS(){
        return msrepo.findAll();
    }

    public MicroService findByName(String servicename){
        return msrepo.findByName(servicename);
    }

    public void serviceaddfileresource(MicroService ms, MultipartFile multipartfile){
        try {
            FileResource fileresource = new FileResource();
            byte[] bytes = multipartfile.getBytes();
            String[] splitter = multipartfile.getContentType().split("/");
            fileresource.setFilename(multipartfile.getOriginalFilename());
            fileresource.setType(splitter[0]);
            fileresource.setExtension(splitter[1]);
            fileresource.setData(bytes);
            fileresource.setMicroserviceId(ms.getId());
            System.out.println(fileresource.getFilename() + " " + fileresource.getType() + " " + fileresource.getExtension() + " " + fileresource.getMicroserviceId());
            fileresourcerepo.save(fileresource);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
