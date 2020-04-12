package kea.schedule.models;

import javax.persistence.*;

@Entity(name= "Action")
@Table(name= "actions")
public class Action implements ModelInterface, MicroServiceElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "actionname")
    private String actionname = null;
    @Column(name = "callbackurl")
    private String callbackurl = null;
    @Column(name = "priority")
    private int priority = 50;
    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
    private MicroService microservice;

    public Action(){

    }

    public Action(int id){
        setId(id);
    }

    public Action(String actionname, String callbackurl){
        this(actionname, callbackurl, 50);
    }

    public Action(String actionname, String callbackurl, int priority){
        this(0, actionname, callbackurl, priority, null);
    }

    public Action(int id, String actionname, String callbackurl, int priority, MicroService microservice){
        this.id = id;
        this.actionname = actionname;
        this.callbackurl = callbackurl;
        this.priority = priority;
        this.microservice = microservice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MicroService getMicroservice() {
        return microservice;
    }

    public void setMicroservice(MicroService microservice) {
        this.microservice = microservice;
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
