package kea.schedule.modules;

import javax.persistence.*;

/**
 * MicroServiceOption's makes it possible to turn on or off special features of the microservice. The microservice options is selectable by the user, and is shown as a checkbox. The value of the checkbox is saved in the according microserviceoption variable.
 * */
@Entity(name= "MicroServiceOption")
@Table(name= "microserviceoptions")
public class MicroServiceOption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String variableName;
    @Column
    private boolean variableValue;
    @Column
    private String description;
    @Column(name = "microserviceid")
    private int microserviceId;
    @Column
    private int priority = 50;
    public MicroServiceOption(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public boolean getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(boolean variableValue) {
        this.variableValue = variableValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVariablevalue() {
        return variableValue;
    }

    public int getMicroserviceId() {
        return microserviceId;
    }

    public void setMicroserviceId(int microserviceId) {
        this.microserviceId = microserviceId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
