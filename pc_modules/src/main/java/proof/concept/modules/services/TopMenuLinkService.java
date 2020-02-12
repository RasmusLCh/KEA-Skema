package proof.concept.modules.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proof.concept.modules.modules.TopMenuLink;
import proof.concept.modules.repositories.TopMenuLinkRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopMenuLinkService {
    TopMenuLinkRepo repo;

    @Autowired
    public TopMenuLinkService(TopMenuLinkRepo repo){
        this.repo = repo;
    }

    public List<TopMenuLink> findAll(){
        List<TopMenuLink> links = repo.findAll();
        if(links == null){
            links = new ArrayList<>();
        }
        return links;
    }
}
