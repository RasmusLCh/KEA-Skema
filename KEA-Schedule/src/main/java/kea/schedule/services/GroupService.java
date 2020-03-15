package kea.schedule.services;

import kea.schedule.moduls.Group;
import kea.schedule.repositories.GroupRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService implements CRUDServiceInterface<Group> {
    private GroupRepo grouprepo;

    public GroupService(GroupRepo grouprepo){
        this.grouprepo = grouprepo;
    }

    @Override
    public Group create(Group group) {
        return grouprepo.save(group);
    }

    @Override
    public void edit(Group group) {
        grouprepo.save(group);
    }

    @Override
    public void delete(int id) {
        Group grp = findById(id);
        if(grp != null){
            grouprepo.delete(grp);
        }
    }

    @Override
    public Group findById(int id) {
        Optional optgroup = grouprepo.findById(id);
        if(optgroup.isPresent()){
            return (Group)optgroup.get();
        }
        return null;
    }

    public List<Group> findByMetadata(String metadata){
        return grouprepo.findByMetadata(metadata);
    }

    public List<Group> findAll(){
        return grouprepo.findAll();
    }
}
