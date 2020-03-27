package kea.schedule.services;

import kea.schedule.moduls.TopMenuLink;
import kea.schedule.repositories.TopMenuLinkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopMenuLinkService implements CRUDServiceInterface<TopMenuLink> {
    TopMenuLinkRepo repo;

    @Autowired
    public TopMenuLinkService(TopMenuLinkRepo repo){
        this.repo = repo;
    }

    @Override
    public TopMenuLink create(TopMenuLink topMenuLink) {
        return repo.save(topMenuLink);
    }

    @Override
    public void edit(TopMenuLink topMenuLink) {
        repo.save(topMenuLink);
    }

    @Override
    public void delete(int id) {
        Optional tml = repo.findById(id);
        if(tml.isPresent()){
            repo.delete((TopMenuLink)tml.get());
        }
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

    public List<TopMenuLink> findAll(String language) {
        return repo.findAll();
    }


}
