package kea.schedule.scheduleservice.components;

import kea.schedule.scheduleservice.models.MSSessionEntity;
import kea.schedule.scheduleservice.services.MSSessionEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;

/**
 * The MSSession works is MS session handler, it works alone on using the userid that is specified from the infrastructure.
 * All anonymous users (userid = 0) shares the save MS session space.
 * */
@Component
@Order(1)
public class MSSession implements Filter {
    private MSSessionEntityService mssservice;
    private MSSessionEntity sessionentity;
    private int userid = 0;

    @Autowired
    public MSSession(MSSessionEntityService mssservice){
        this.mssservice = mssservice;
        if(this.userid == 0){
            sessionentity = new MSSessionEntity();
        }
        else{
            sessionentity = mssservice.findByUserId(userid);
            if(sessionentity == null){
                sessionentity = mssservice.create(new MSSessionEntity(userid));
            }
        }
    }

    /**
     * Gets an attribute
     * */
    public Object getAttribute(String str){
        System.out.println("moo");
        if(userid == 0 || sessionentity == null){
            return null;
        }
        return sessionentity.getAttributes().get(str);

    }
    /**
     * Sets an attribute
     * */
    public Object setAttribute(String str, Object obj){
        if(userid == 0 || sessionentity == null){
            throw new RuntimeException("userid == 0!");
        }
        return sessionentity.getAttributes().put(str, obj);
    }

    public int getUserId(){
        return userid;
    }

    /**
     * The filter is used to catch the userid that the infrastructure sends to the microservice.
     * */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter START!");
        Enumeration<String> para = servletRequest.getParameterNames();
        while(para.hasMoreElements()){
            String p = para.nextElement();
            System.out.println("para = " + p + servletRequest.getParameter(p));
        }
        Enumeration<String> attr = servletRequest.getParameterNames();
        while(attr.hasMoreElements()){
            String a = attr.nextElement();
            System.out.println("attr = " + a + servletRequest.getAttribute(a));
        }
        if(servletRequest.getParameter("userid") != null){
            System.out.println("Setting user id!");
            try {
                userid = Integer.parseInt(servletRequest.getParameter("userid"));
                System.out.println("user id is set to " + userid);
            }
            catch(NumberFormatException  e){
                userid = 0;
            }
        }
        else{
            System.out.println("userid = 0");
            userid = 0;
        }
        if(userid != 0){
            this.sessionentity = mssservice.findByUserId(userid);
            if(this.sessionentity == null){
                this.sessionentity = new MSSessionEntity(userid);
                this.sessionentity = mssservice.create(sessionentity);
            }
        }
        System.out.println("Session id is " + this.sessionentity.getId());
        //This is important, else the next part in the chain wont be called and any controllers wont get the request.
        filterChain.doFilter(servletRequest, servletResponse);
        //Ok lets save if we need to, since the call is over
        if(sessionentity != null){
            sessionentity.setExpiretime(System.currentTimeMillis() + 1800000);
            mssservice.edit(sessionentity);
        }
    }
}
