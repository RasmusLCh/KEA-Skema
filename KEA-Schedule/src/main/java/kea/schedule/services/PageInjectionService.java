package kea.schedule.services;

import kea.schedule.moduls.PageInjection;
import kea.schedule.repositories.PageInjectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * There is no error handling on page injections, since they shouldnt be injected if the user doesnt have access to the page in the first case.
 * */
@Service
public class PageInjectionService implements CRUDServiceInterface<PageInjection> {
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

    @Override
    public PageInjection create(PageInjection pageInjection) {
        return pageinectionrepo.save(pageInjection);
    }

    @Override
    public void edit(PageInjection pageInjection) {
        pageinectionrepo.save(pageInjection);
    }

    @Override
    public void delete(int id) {
        Optional optpageInjection = pageinectionrepo.findById(id);
        if(optpageInjection.isPresent()){
            pageinectionrepo.delete((PageInjection)optpageInjection.get());
        }
    }

    @Override
    public PageInjection findById(int id) {
        Optional optpageInjection = pageinectionrepo.findById(id);
        if(optpageInjection.isPresent()){
            return (PageInjection)optpageInjection.get();
        }
        return null;
    }

    @Override
    public List<PageInjection> findAll() {
        return pageinectionrepo.findAll();
    }
}
