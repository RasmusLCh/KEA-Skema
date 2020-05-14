package microservice.infrastructure.services;

import microservice.infrastructure.models.TopMenuLink;
import microservice.infrastructure.repositories.TopMenuLinkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CRUD for TopMenuLink
 * */

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
        actionservice.doAction("TopMenuLinkService.create", topMenuLink);
        return newtml;
    }

    @Override
    public void edit(TopMenuLink topMenuLink) {
        repo.save(topMenuLink);
        actionservice.doAction("TopMenuLinkService.edit", topMenuLink);
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
        actionservice.doAction("TopMenuLinkService.delete", new TopMenuLink(id));
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

    @Override
    public List<TopMenuLink> findAll(int msid) {
        return repo.findAllByMicroserviceIdOrderByPriority(msid);
    }

    /**
     * Only Top Menu Links that the current user has access too, are returned
     * */
    public List<TopMenuLink> findAllByLanguageAndAccess(String language) {
        List<TopMenuLink> list = repo.findAllByLanguageAndMicroserviceEnabledIsTrueOrderByPriority(language.toLowerCase());
        List<TopMenuLink> alist = new ArrayList<>();
        for(TopMenuLink tml: list){
            if(authservice.hasAccess(tml)){
                alist.add(tml);
            }
        }
        return alist;
    }


}
