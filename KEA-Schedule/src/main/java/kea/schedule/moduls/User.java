package kea.schedule.moduls;

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
    @Column(unique=true, name = "identifier", columnDefinition="VARCHAR(50)")
    @Size(min=1,max=50)
    @NotNull
    private String identifier; //This is a unique user identifier
    @Column(name = "language", columnDefinition="VARCHAR(10)")
    @Size(min=1,max=10)
    @NotNull
    private String language; //Default language for user
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "group_user", inverseJoinColumns = @JoinColumn(name="group_id", referencedColumnName="id", table="groups"), joinColumns = @JoinColumn(name="user_id", referencedColumnName="id", table="users"))
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
}
