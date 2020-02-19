package kea.schedule.services;

import kea.schedule.moduls.Group;
import kea.schedule.repositories.GroupRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private GroupRepo grouprepo;

    public GroupService(GroupRepo grouprepo){
        this.grouprepo = grouprepo;
    }

    public List<Group> findAll(){
        return grouprepo.findAll();
    }
}
