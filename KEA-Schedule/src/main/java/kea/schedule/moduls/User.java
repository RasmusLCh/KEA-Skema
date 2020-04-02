package kea.schedule.moduls;

import net.minidev.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name= "User")
@Table(name= "users")
public class User implements ModelInterface{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "displayname", columnDefinition="VARCHAR(255)")
    @Size(min=1,max=255)
    @NotNull
    private String displayname;
    @Column(unique=true, name = "email", columnDefinition="VARCHAR(100)")
    @Size(min=1,max=100)
    @NotNull
    private String email; //This is a unique user identifier
    @Column(unique=true, name = "identifier", columnDefinition="VARCHAR(50)")
    @Size(min=1,max=50)
    @NotNull
    private String identifier; //This is a unique user identifier
    @Column(name = "language", columnDefinition="VARCHAR(10)")
    @Size(min=1,max=10)
    @NotNull
    private String language; //Default language for user
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(
        name = "group_user",
        joinColumns = @JoinColumn(name="user_id", referencedColumnName="id", table="users"),
        inverseJoinColumns = @JoinColumn(name="group_id", referencedColumnName="id", table="pre_groups")
    )
    private List<Group> groups;


    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Group){
            return ((User)obj).getId() == this.id;
        }
        return false;
    }

    public JSONObject appendJSON(JSONObject obj){
        obj.appendField("id", getId());

        return obj;
    }
}
