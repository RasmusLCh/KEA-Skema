package kea.schedule.services;

import kea.schedule.moduls.PageInjection;
import kea.schedule.repositories.PageInjectionRepo;
import net.minidev.json.JSONObject;
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
    private ActionService actionservice;

    @Autowired
    public PageInjectionService(PageInjectionRepo pageinectionrepo, ActionService actionservice){
        this.pageinectionrepo = pageinectionrepo;
        this.actionservice = actionservice;
    }


    public List<PageInjection> getCSSPageInjection(String page){
        if(page.equals("/")){
            page = "/index";
        }
        return pageinectionrepo.findByPageAndTypeAndMicroserviceEnabledIsTrue(page, "CSS");
    }

    public List<PageInjection> getJSPageInjection(String page){
        if(page.equals("/")){
            page = "/index";
        }
        return pageinectionrepo.findByPageAndTypeAndMicroserviceEnabledIsTrue(page, "JS");
    }

    @Override
    public PageInjection create(PageInjection pageInjection) {
        PageInjection newpi = pageinectionrepo.save(pageInjection);
        actionservice.doAction("PageInjectionService.create", newpi.toJSON(new JSONObject()));
        return newpi;
    }

    @Override
    public void edit(PageInjection pageInjection) {
        pageinectionrepo.save(pageInjection);
        actionservice.doAction("PageInjectionService.edit", pageInjection.toJSON(new JSONObject()));

    }

    @Override
    public void delete(int id) {
        /*
        Optional optpageInjection = pageinectionrepo.findById(id);
        if(optpageInjection.isPresent()){
            pageinectionrepo.delete((PageInjection)optpageInjection.get());
        }

         */
        pageinectionrepo.deleteById(id);
        actionservice.doAction("PageInjectionService.delete", new JSONObject().appendField("id", id));
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
