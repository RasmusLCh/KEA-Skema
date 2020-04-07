package kea.schedule.moduls;

import net.minidev.json.JSONObject;

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
    @ManyToOne
    private MicroService microservice;

    public Action(){

    }

    public Action(String actionname, String callbackurl){

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

    public JSONObject toJSON(JSONObject obj){
        return toJSON(obj, false);
    }

    @Override
    public JSONObject toJSON(JSONObject obj, boolean recursive) {
        obj.appendField("id", getId());
        obj.appendField("callbackurl", getCallbackurl());
        obj.appendField("actionname", getActionname());
        obj.appendField("priority", getPriority());
        if(recursive){
            if(getMicroservice() != null){
                obj.appendField("microservice", getMicroservice().toJSON(new JSONObject()));
            }
        }
        else{
            if(getMicroservice() != null){
                obj.appendField("microservice", getMicroservice().getId());
            }
        }
        //Groups
        return obj;
    }
}
