package kea.schedule.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MicroServiceOption's makes it possible to turn on or off special features of the microservice. The microservice options is selectable by the user, and is shown as a checkbox. The value of the checkbox is saved in the according microserviceoption variable.
 * */
@Entity(name= "MicroServiceOption")
@Table(name= "microserviceoptions")
public class MicroServiceOption implements MicroServiceElement, ModelInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String name;
    @Column
    private boolean active;
    @Column
    private String description;
    @ManyToOne(cascade= {CascadeType.DETACH})
    private MicroService microservice;
    @Column
    private int priority = 50;


    public MicroServiceOption(){}

    public MicroServiceOption(int id){
        setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive(){
        return getActive();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MicroService getMicroservice() {
        return microservice;
    }

    public void setMicroservice(MicroService microservice) {
        this.microservice = microservice;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
