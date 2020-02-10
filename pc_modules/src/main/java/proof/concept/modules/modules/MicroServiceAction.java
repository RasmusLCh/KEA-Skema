package proof.concept.modules.modules;

import org.springframework.stereotype.Controller;

import javax.persistence.*;

@Entity(name= "MicroServiceAction")
@Table(name= "microserviceactions")
public class MicroServiceAction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "servicename")
    private String servicename;
    @Column(name = "actionname")
    private String actionname;
    @Column(name = "rest")
    private String rest;
    @Column(name = "priority")
    private int priority;

    public MicroServiceAction(){
        this(null, null, null, -1);
    }

    public MicroServiceAction(String servicename, String actionname, String rest, int priority){
        this(-1, servicename, actionname, rest, priority);
    }

    public MicroServiceAction(int id, String servicename, String actionname, String rest, int priority){
        this.id = id;
        this.servicename = servicename;
        this.actionname = actionname;
        this.rest = rest;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getActionname() {
        return actionname;
    }

    public void setActionname(String actionname) {
        this.actionname = actionname;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
