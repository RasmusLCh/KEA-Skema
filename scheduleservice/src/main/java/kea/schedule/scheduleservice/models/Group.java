package kea.schedule.scheduleservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.minidev.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name= "Group")
@Table(name= "pre_groups") //JPA / Hibernate doesnt allow the table to be called groups..
public class Group implements ModelInterface{
    @Id
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
    private List<Group> groups = new ArrayList();
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    //We want this to be unidirectional, so we can add/remove from users also.
    @JoinTable(name = "group_user", joinColumns = @JoinColumn(name="group_id", referencedColumnName="id", table="pre_groups"), inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName="id", table="users"))
    private List<User> users = new ArrayList();;

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

    @Override
    public JSONObject toJSON(JSONObject obj) {
        return toJSON(obj, false);
    }

    public JSONObject toJSON(JSONObject obj, boolean recursive) {
        obj.appendField("id", getId());
        obj.appendField("name", getName());
        obj.appendField("metadata", getMetadata());
        obj.appendField("description", getDescription());
        if(recursive) {
            JSONObject grps = new JSONObject();
            for (Group group : groups) {
                grps.appendField(Integer.toString(group.getId()), group.toJSON(new JSONObject()));
            }
            obj.appendField("groups", grps);
            JSONObject usrs = new JSONObject();
            for (User user : users) {
                usrs.appendField(Integer.toString(user.getId()), user.toJSON(new JSONObject()));
            }
            obj.appendField("users", usrs);
        }
        else{
            JSONObject grps = new JSONObject();
            for (Group group : groups) {
                grps.appendField(Integer.toString(group.getId()), group.getName());
            }
            obj.appendField("groups", grps);
            JSONObject usrs = new JSONObject();
            for (User user : users) {
                usrs.appendField(Integer.toString(user.getId()), user.getIdentifier());
            }
            obj.appendField("users", usrs);
        }
        return obj;
    }
}
