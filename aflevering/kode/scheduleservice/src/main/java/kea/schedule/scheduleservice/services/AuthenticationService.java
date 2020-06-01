package kea.schedule.scheduleservice.services;

import kea.schedule.scheduleservice.models.Group;
import kea.schedule.scheduleservice.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles user authentication.
 * */


@Service
public class AuthenticationService {
    private UserService userservice;
    private GroupService groupservice;

    public AuthenticationService(UserService userservice, GroupService groupservice){
        this.userservice = userservice;
        this.groupservice = groupservice;
    }

    /**
     * Returns if the user has access to the group specified by name.
     *
     * @Param   user    The user that is requesting access
     * @Param   accessrequired    The group, that the user is requesting access too.
     * */
    public boolean hasAccess(User user, Group accessrequired){
        if(user == null || accessrequired == null){
            return false;
        }
        boolean returnval = false;
        for(Group grp : user.getGroups()){
            if(grp.equals(accessrequired)){
                return true;
            }
            returnval = inGroup(grp, accessrequired, 50);
            if(returnval){
                return true;
            }
        }
        return false;
    }
    /**
     * Returns if the user has access to the group specified by name.
     *
     * @Param   user    The user that is requesting access
     * @Param   accessrequired    The group specified by name, that the user is requesting access too.
     * */
    public boolean hasAccess(User user, String accessrequired){
        return hasAccess(user, groupservice.findByName(accessrequired));
    }
    /**
     * Returns if the current user has access to the Group.
     *
     * @Param   accessrequired    The Group the user is requesting access too.
     * */
    public boolean hasAccess(Group accessrequired){
        return hasAccess(getUser(), accessrequired);
    }
    /**
     * Returns if the current user has access to the Group.
     *
     * @Param   accessrequired    The Group the user is requesting access too by name.
     * */
    public boolean hasAccess(String accessrequired){
        return hasAccess(getUser(), accessrequired);
    }

    /**
     * Returns if the current user has access to one or more of the groups Groups.
     *
     * @Param   accessrequired    The Groups the user is requesting access.
     * */
    public boolean hasAccess(List<Group> accessrequired){
        User user = getUser();
        if(getUser() != null){
            boolean returnval = false;
            for(Group accessgrp : accessrequired){
                returnval = hasAccess(user, accessgrp);
                if(returnval){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean inGroup(Group sgrp, Group accessrequired, int maxrecur){
        if(maxrecur == 0){
            throw new RuntimeException("Maximum group reucursion reached! This indicate a circulair group structure.");
        }
        boolean returnval = false;
        for(Group grp: sgrp.getGroups()){
            if(grp.equals(accessrequired)){
                return true;
            }
            returnval = inGroup(grp, accessrequired, maxrecur-1);
            if(returnval){
                return true;
            }
        }
        return false;
    }

    private User getUser(){
        return userservice.getCurrentUser();
    }
}
