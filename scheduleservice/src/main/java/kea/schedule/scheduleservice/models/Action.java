package kea.schedule.scheduleservice.models;

import javax.persistence.*;

@Entity(name= "Action")
@Table(name= "actions")
public class Action implements ModelInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "actionname")
    private String actionname = null;
    @Column(name = "callbackurl")
    private String callbackurl = null;
    @Column(name = "priority")
    private int priority = 50;

    public Action(){

    }

    public Action(int id){
        setId(id);
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Action){
            return ((Action)obj).getId() == this.id;
        }
        return false;
    }

    public void setId(int id) {
        this.id = id;
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
