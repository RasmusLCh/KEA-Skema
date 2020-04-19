package kea.schedule.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import kea.schedule.models.*;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Properties;

@Service
public class AuthenticationService {

    private UserService userservice;
    private GroupService groupservice;
    private HttpSession session;
    private MicroServiceService microserviceservice;
    private ActionService actionservice;

    @Autowired
    public AuthenticationService(UserService userservice, GroupService groupservice, MicroServiceService microserviceservice,HttpSession session, ActionService actionservice){
        this.userservice = userservice;
        this.groupservice = groupservice;
        this.microserviceservice = microserviceservice;
        this.session = session;
        this.actionservice = actionservice;
    }

    private User getUser(){
        return (User)session.getAttribute("curuser");
    }

    public boolean isAuthenticated(){
        return session.getAttribute("curuser") != null;
    }

    public boolean isAdmin(){
        MicroService ms = microserviceservice.findByName("adminpanel");
        if(ms == null){
            System.out.println("Everybody is currently an administrator. Please create a MicroService named adminpanel that uses port 0!");
            return true;
        }
        if(ms.getPort() == 0){
            return hasAccess(ms);
        }
        System.out.println("Everybody is currently an administrator. The adminpanel must be defined as an internal service running on port 0!");
        return true;
    }

    public boolean hasAccess(MicroService ms){
        if(ms == null){
            return false;
        }
        return hasAccess(ms.getAccessgroups());
    }
    /**
     * Being a MicroServiceElement impliece that access is only available if the user has access to the MicroService
     * */
    public boolean hasAccess(MicroServiceElement element){
        if(element == null){
            return false;
        }
        return hasAccess(element.getMicroservice().getAccessgroups());
    }


    public boolean hasAccess(User user, String accessrequired){
        return hasAccess(user, groupservice.findByName(accessrequired));
    }

    public boolean hasAccess(Group accessrequired){
        return hasAccess(getUser(), accessrequired);
    }

    public boolean hasAccess(String accessrequired){
        return hasAccess(getUser(), accessrequired);
    }

    /**
     * The user has access to one or more of the groups
     * */
    public boolean hasAccess(List<Group> accessrequired){
return true;
/*
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

 */
    }


    public boolean hasAccess(User user, Group accessrequired){
return true;
/*
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

 */
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




    public boolean Authenticate(String identifier, String password){
        User user = userservice.findByIdentifier(identifier);
        return Authenticate(user, password);
    }

    public boolean Authenticate(User user, String password){
        boolean returnvalue = true;
        if(user == null){
            returnvalue = false;
        }
        if(returnvalue)
        {
            returnvalue = auth(user.getEmail(), password);
            if(returnvalue && session != null){
                session.setAttribute("curuser", user);
            }
        }
        if(returnvalue){
            ObjectMapper mapper = new ObjectMapper();
            JSONObject data = new JSONObject();
            data.appendField("user", mapper.convertValue(user, JSONObject.class));
            data.appendField("result", returnvalue);
            actionservice.doAction("AuthenticationService.Authenticate", data);
        }
        return returnvalue;
    }

    //Inspired by https://stackoverflow.com/questions/3060837/validate-smtp-server-credentials-using-java-without-actually-sending-mail
    private boolean auth(String email, String password){
        Properties properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
        try{
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.office365.com", 587, email, password);
            transport.close();
            System.out.println("success");
            return true;
        }
        catch(AuthenticationFailedException e) {
            System.out.println("AuthenticationFailedException - for authentication failures");
            e.printStackTrace();
        }
        catch(MessagingException e) {
            System.out.println("for other failures");
            e.printStackTrace();
        }
        return false;
    }
}
