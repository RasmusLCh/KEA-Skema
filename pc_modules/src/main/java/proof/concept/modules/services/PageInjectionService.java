package proof.concept.modules.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proof.concept.modules.modules.PageInjection;
import proof.concept.modules.repositories.PageInjectionRepo;

import java.util.List;

@Service
public class PageInjectionService {
    private PageInjectionRepo pageinectionrepo;

    @Autowired
    public PageInjectionService(PageInjectionRepo pageinectionrepo){
        this.pageinectionrepo = pageinectionrepo;
    }

    public List<PageInjection> getCSSPageInjection(String page){
        return pageinectionrepo.findByPageAndType(page, "CSS");
    }

    public List<PageInjection> getJSPageInjection(String page){
        return pageinectionrepo.findByPageAndType(page, "JS");
    }
}
