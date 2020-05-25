package kea.schedule.scheduleservice.controllers;

import kea.schedule.scheduleservice.models.Group;
import kea.schedule.scheduleservice.models.User;
import kea.schedule.scheduleservice.services.GroupService;
import kea.schedule.scheduleservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actions/")
public class ActionController {
    UserService userservice;
    GroupService groupservice;

    @Autowired
    public ActionController(UserService userservice, GroupService groupservice){
        this.userservice = userservice;
        this.groupservice = groupservice;
    }

    @PostMapping("group/create/")
    public ResponseEntity post_group_create(@RequestBody Group grp){
        for(int i = 0; i < grp.getUsers().size(); i++){
            User usr = userservice.findById(grp.getUsers().get(i).getId());
            grp.getUsers().set(i, usr);
        }
        for(int i = 0; i < grp.getGroups().size(); i++){
            Group dgrp = groupservice.findById(grp.getGroups().get(i).getId());
            grp.getGroups().set(i, dgrp);
        }
        groupservice.create(grp);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("group/edit/")
    public ResponseEntity post_group_edit(@RequestBody Group grp){
        for(int i = 0; i < grp.getUsers().size(); i++){
            User usr = userservice.findById(grp.getUsers().get(i).getId());
            grp.getUsers().set(i, usr);
        }
        for(int i = 0; i < grp.getGroups().size(); i++){
            Group dgrp = groupservice.findById(grp.getGroups().get(i).getId());
            grp.getGroups().set(i, dgrp);
        }
        groupservice.edit(grp);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("group/delete/")
    public ResponseEntity post_group_delete(@RequestBody Group grp){
        groupservice.delete(grp.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("user/create/")
    public ResponseEntity post_user_create(@RequestBody User usr){
        for(int i = 0; i < usr.getGroups().size(); i++){
            Group dgrp = groupservice.findById(usr.getGroups().get(i).getId());
            usr.getGroups().set(i, dgrp);
        }
        userservice.create(usr);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("user/edit/")
    public ResponseEntity post_user_edit(@RequestBody User usr){
        for(int i = 0; i < usr.getGroups().size(); i++){
            Group dgrp = groupservice.findById(usr.getGroups().get(i).getId());
            usr.getGroups().set(i, dgrp);
        }
        userservice.edit(usr);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("user/delete/")
    public ResponseEntity post_user_delete(@RequestBody User usr){
        userservice.delete(usr.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /*
    *
    * 24504 	GroupService.create 	http://localhost:7510/actions/group/create/ 	50 	VIEW 	EDIT 	DELETE
24505 	GroupService.edit 	http://localhost:7510/actions/group/edit/ 	50 	VIEW 	EDIT 	DELETE
24506 	GroupService.delete 	http://localhost:7510/actions/group/delete/ 	50 	VIEW 	EDIT 	DELETE
24507 	UserService.create 	http://localhost:7510/actions/user/create/ 	50 	VIEW 	EDIT 	DELETE
24508 	UserService.edit 	http://localhost:7510/actions/user/edit/ 	50 	VIEW 	EDIT 	DELETE
24509 	UserService.delete 	http://localhost:7510/actions/user/delete/ 	50
    * */
}
