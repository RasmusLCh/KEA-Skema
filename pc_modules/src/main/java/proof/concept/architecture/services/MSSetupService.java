package proof.concept.architecture.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import proof.concept.architecture.modules.*;
import proof.concept.architecture.repositories.*;

import java.io.IOException;
import java.util.List;

@Service
public class MSSetupService {

    MSSetupRepo msrepo;
    ActionRepo actionrepo;
    PageInjectionRepo pageinjectionrepo;
    TopMenuLinkRepo topmenulinkrepo;
    FileResourceRepo fileresourcerepo;

    @Autowired
    public MSSetupService(MSSetupRepo msrepo, ActionRepo actionrepo, PageInjectionRepo pageinjectionrepo, TopMenuLinkRepo topmenulinkrepo, FileResourceRepo fileresourcerepo){
        this.msrepo = msrepo;
        this.actionrepo = actionrepo;
        this.pageinjectionrepo = pageinjectionrepo;
        this.topmenulinkrepo = topmenulinkrepo;
        this.fileresourcerepo = fileresourcerepo;
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

    public List<TopMenuLink> serviceaddtopmenulink(TopMenuLink link){
        topmenulinkrepo.save(link);
        return topmenulinkrepo.findAll();
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
            fileresource.setMicroservice_id(ms.getId());
            System.out.println(fileresource.getFilename() + " " + fileresource.getType() + " " + fileresource.getExtension() + " " + fileresource.getMicroservice_id());
            fileresourcerepo.save(fileresource);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
