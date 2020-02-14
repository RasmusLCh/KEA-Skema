package kea.schedule.modules;

import javax.persistence.*;

@Entity(name= "Action")
@Table(name= "actions")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "actionname")
    private String actionname = null;
    @Column(name = "callbackurl")
    private String callbackurl = null;
    @Column(name = "priority")
    private int priority = 50;
    @Column
    private int microserviceid;

    public Action(){

    }

    public Action(String actionname, String callbackurl){

    }

    public Action(String actionname, String callbackurl, int priority){
        this(0, actionname, callbackurl, priority, 0);
    }

    public Action(int id, String actionname, String callbackurl, int priority, int microservice_id){
        this.id = id;
        this.actionname = actionname;
        this.callbackurl = callbackurl;
        this.priority = priority;
        this.microserviceid = microservice_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMicroserviceid() {
        return microserviceid;
    }

    public void setMicroserviceid(int microservice_id) {
        this.microserviceid = microservice_id;
    }

    public String getActionname() {
        return actionname;
    }

    public void setActionname(String actionname) {
        this.actionname = actionname;
    }

    public String getCallbackurl() {
        return callbackurl;
    }

    public void setCallbackurl(String rest) {
        this.callbackurl = rest;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
