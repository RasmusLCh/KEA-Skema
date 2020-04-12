package kea.schedule.services;

import kea.schedule.models.Group;
import kea.schedule.repositories.GroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService implements CRUDServiceInterface<Group> {
    private GroupRepo grouprepo;
    private ActionService actionservice;

    @Autowired
    public GroupService(GroupRepo grouprepo, ActionService actionservice){
        this.grouprepo = grouprepo;
        this.actionservice = actionservice;
    }

    @Override
    public Group create(Group group) {
        Group newgrp = grouprepo.save(group);
        actionservice.doAction("GroupService.create", group);
        return newgrp;
    }

    @Override
    public void edit(Group group) {
        grouprepo.save(group);
        actionservice.doAction("GroupService.edit", group);
    }

    @Override
    public void delete(int id) {
        /*
        Group grp = findById(id);
        if(grp != null){
            grouprepo.delete(grp);
        }

         */
        grouprepo.deleteById(id);
        actionservice.doAction("GroupService.edit", new Group(id));
    }

    @Override
    public Group findById(int id) {
        Optional optgroup = grouprepo.findById(id);
        if(optgroup.isPresent()){
            return (Group)optgroup.get();
        }
        return null;
    }

    public Group findByName(String name){
        return grouprepo.findByName(name);
    }

    public List<Group> findByMetadata(String metadata){
        return grouprepo.findByMetadata(metadata);
    }

    public List<Group> findAll(){
        return grouprepo.findAll();
    }
}
