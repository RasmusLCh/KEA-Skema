package kea.schedule.services;

import kea.schedule.moduls.Group;
import kea.schedule.moduls.User;
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

    @Autowired
    public AuthenticationService(UserService userservice, GroupService groupservice, HttpSession session){
        this.userservice = userservice;
        this.groupservice = groupservice;
        this.session = session;
    }

    private User getUser(){
        return (User)session.getAttribute("curuser");
    }

    public boolean isAuthenticated(){
        return session.getAttribute("curuser") != null;
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
        if(user == null){
            return false;
        }
        boolean returnvalue = auth(user.getEmail(), password);
        if(returnvalue && session != null){
            session.setAttribute("curuser", user);
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
