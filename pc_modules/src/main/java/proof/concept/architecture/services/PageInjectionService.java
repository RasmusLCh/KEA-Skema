package proof.concept.architecture.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proof.concept.architecture.modules.PageInjection;
import proof.concept.architecture.repositories.PageInjectionRepo;

import java.util.List;

@Service
public class PageInjectionService {
    private PageInjectionRepo pageinectionrepo;

    @Autowired
    public PageInjectionService(PageInjectionRepo pageinectionrepo){
        this.pageinectionrepo = pageinectionrepo;
    }

    public List<PageInjection> getCSSPageInjection(String page){
        if(page.equals("/")){
            page = "/index";
        }
        return pageinectionrepo.findByPageAndType(page, "CSS");
    }

    public List<PageInjection> getJSPageInjection(String page){
        if(page.equals("/")){
            page = "/index";
        }
        return pageinectionrepo.findByPageAndType(page, "JS");
    }
}
