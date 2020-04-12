package kea.schedule.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kea.schedule.converters.deserialize.ListGroupDeserializer;
import kea.schedule.converters.deserialize.ListUserDeserializer;
import kea.schedule.converters.serialize.ListGroupSerializer;
import kea.schedule.converters.serialize.ListUserSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;
import java.util.ArrayList;

@Entity(name= "Group")
@Table(name= "pre_groups") //JPA / Hibernate doesnt allow the table to be called groups..
public class Group implements ModelInterface{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    //Name is the same as the Role the group gives access to
    @Column(unique=true, columnDefinition="VARCHAR(100)")
    @Size(min=1,max=100)
    @NotNull
    private String name = null;
    @Column(columnDefinition="VARCHAR(2000)")
    @Size(max=2000)
    private String description = null;
    @Column(columnDefinition="VARCHAR(500)")
    @Size(min=0,max=100)
    private String metadata = null;
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JsonSerialize(converter = ListGroupSerializer.class)
    @JsonDeserialize(converter = ListGroupDeserializer.class)
    private List<Group> groups = new ArrayList();
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    //We want this to be unidirectional, so we can add/remove from users also.
    @JoinTable(name = "group_user", joinColumns = @JoinColumn(name="group_id", referencedColumnName="id", table="pre_groups"), inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName="id", table="users"))
    @JsonSerialize(converter = ListUserSerializer.class)
    @JsonDeserialize(converter = ListUserDeserializer.class)
    private List<User> users = new ArrayList();

    public Group(){

    }

    public Group(int id){
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

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Group){
            return ((Group)obj).getId() == this.id;
        }
        return false;
    }
}
