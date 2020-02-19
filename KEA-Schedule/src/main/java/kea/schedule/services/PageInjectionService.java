package kea.schedule.services;

import kea.schedule.moduls.PageInjection;
import kea.schedule.repositories.PageInjectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
