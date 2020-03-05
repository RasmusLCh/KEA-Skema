package kea.schedule.moduls;

import javax.persistence.*;

@Entity(name= "MicroService")
@Table(name= "microservices")
public class MicroService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="name", columnDefinition="VARCHAR(50)", unique = true)
    private String name = "balue";
    @Column(name="port", unique = true)
    private int port;
    @Column
    private float version = 0f;
    @Column
    private String description = "";
    @Column
    //This refered to if the service should be required for users or not
    private boolean userRequired;
    @Column(name="enabled")
    private boolean enabled = false;
    //If the microservice requires another microservice, an id for the microservice should be specified.
    @Column
    private int dependencyMicroserviceId;

    public MicroService(){ }

    public MicroService(String name, int port){
        this(0, name, port);
    }

    public MicroService(int id, String name, int port){
        this.id = id;
        this.name = name;
        this.port = port;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isEnabled(){ return this.enabled; }

    public void setEnabled(boolean enabled){ this.enabled = enabled; }

    public float getVersion() {
        return version;
    }

    public void setVersion(float version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUserRequired() {
        return userRequired;
    }

    public void setUserRequired(boolean userRequired) {
        this.userRequired = userRequired;
    }

    public int getDependencyMicroserviceId() {
        return dependencyMicroserviceId;
    }

    public void setDependencyMicroserviceId(int dependencyMicroserviceId) {
        this.dependencyMicroserviceId = dependencyMicroserviceId;
    }
}
