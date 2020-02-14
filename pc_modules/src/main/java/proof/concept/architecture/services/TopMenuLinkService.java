package proof.concept.architecture.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proof.concept.architecture.modules.TopMenuLink;
import proof.concept.architecture.repositories.TopMenuLinkRepo;

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
