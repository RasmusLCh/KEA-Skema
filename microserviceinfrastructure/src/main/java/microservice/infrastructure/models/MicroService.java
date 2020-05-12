package microservice.infrastructure.models;

import javax.persistence.*;
import java.util.List;

@Entity(name= "MicroService")
@Table(name= "microservices")
public class MicroService implements ModelInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="name", columnDefinition="VARCHAR(50)", unique = true)
    private String name = "";
    @Column(name="port", unique = true)
    private int port = 0;
    @Column
    private float version = 0f;
    @Column
    private String description = "";
    @Column(name="enabled", columnDefinition = "boolean default false")
    private boolean enabled = false;
    //If the microservice requires another microservice, an id for the microservice should be specified.
    @Column
    private int dependencyMicroserviceId;
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(
            name = "microservice_group",
            joinColumns = @JoinColumn(name="microservice_id", referencedColumnName="id", table="microservices"),
            inverseJoinColumns = @JoinColumn(name="group_id", referencedColumnName="id", table="pre_groups")
    )
    private List<Group> accessgroups;
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "microservice", orphanRemoval = true)
    private List<Action> actions;
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "microservice", orphanRemoval = true)
    private List<FileResource> fileresources;
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "microservice", orphanRemoval = true)
    private List<MicroServiceOption> microserviceoptions;
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "microservice", orphanRemoval = true)
    private List<PageInjection> pageinjections;
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "microservice", orphanRemoval = true)
    private List<TopMenuLink> topmenulinks;

    public MicroService(){ }

    public MicroService(String name, int port){
        this(0, name, port);
    }

    public MicroService(int id){
        this(id, "", 0);
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
    public boolean getEnabled(){ return this.enabled; }
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

    public int getDependencyMicroserviceId() {
        return dependencyMicroserviceId;
    }

    public void setDependencyMicroserviceId(int dependencyMicroserviceId) {
        this.dependencyMicroserviceId = dependencyMicroserviceId;
    }

    public List<Group> getAccessgroups() {
        return accessgroups;
    }

    public void setAccessgroups(List<Group> accessgroups) {
        this.accessgroups = accessgroups;
    }

    public boolean inAccessgroups(Group grp){
        return accessgroups.contains(grp);
    }

    public List<MicroServiceOption> getMicroserviceoptions() {
        return microserviceoptions;
    }
}
