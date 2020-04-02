package kea.schedule.services;

import kea.schedule.moduls.TopMenuLink;
import kea.schedule.repositories.TopMenuLinkRepo;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopMenuLinkService implements CRUDServiceInterface<TopMenuLink> {
    private TopMenuLinkRepo repo;
    private AuthenticationService authservice;
    private ActionService actionservice;

    @Autowired
    public TopMenuLinkService(TopMenuLinkRepo repo, AuthenticationService authservice, ActionService actionservice){
        this.repo = repo;
        this.authservice = authservice;
        this.actionservice = actionservice;
    }

    @Override
    public TopMenuLink create(TopMenuLink topMenuLink) {
        TopMenuLink newtml = repo.save(topMenuLink);
        actionservice.doAction("TopMenuLinkService.create", topMenuLink.toJSON(new JSONObject()));
        return newtml;
    }

    @Override
    public void edit(TopMenuLink topMenuLink) {
        repo.save(topMenuLink);
        actionservice.doAction("TopMenuLinkService.edit", topMenuLink.toJSON(new JSONObject()));
    }

    @Override
    public void delete(int id) {
        /*
        Optional tml = repo.findById(id);
        if(tml.isPresent()){
            repo.delete((TopMenuLink)tml.get());
        }

         */
        repo.deleteById(id);
        actionservice.doAction("TopMenuLinkService.delete", new JSONObject().appendField("id", id));
    }

    @Override
    public TopMenuLink findById(int id) {
        Optional tml = repo.findById(id);
        if(tml.isPresent()){
            return (TopMenuLink) tml.get();
        }
        return null;
    }

    @Override
    public List<TopMenuLink> findAll() {
        return repo.findAll();
    }

    /**
     * Only Top Menu Links that the current user has access too, are returned
     * */
    public List<TopMenuLink> findAllByLanguageAndAccess(String language) {
        List<TopMenuLink> list = repo.findAllByLanguageOrderByPriority(language.toLowerCase());
        List<TopMenuLink> alist = new ArrayList<>();
        for(TopMenuLink tml: list){
            if(authservice.hasAccess(tml)){
                alist.add(tml);
            }
        }
        return alist;
    }


}
