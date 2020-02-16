package kea.schedule.services;

import kea.schedule.modules.TopMenuLink;
import kea.schedule.repositories.TopMenuLinkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopMenuLinkService {
    TopMenuLinkRepo repo;

    @Autowired
    public TopMenuLinkService(TopMenuLinkRepo repo){
        this.repo = repo;
    }

    public List<TopMenuLink> findAll(String language){
        List<TopMenuLink> links = repo.findAllByLanguageOrderByPriority(language);
        if(links == null){
            links = new ArrayList<>();
        }
        return links;
    }
}
