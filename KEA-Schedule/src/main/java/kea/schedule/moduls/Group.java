package kea.schedule.moduls;

import javax.persistence.*;

import java.util.List;
import java.util.ArrayList;

@Entity(name= "Group")
@Table(name= "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique=true)
    private String name = null;
    @ManyToMany
    List<Group> groups = new ArrayList();
    @ManyToMany
    List<User> users = new ArrayList();;
    @OneToMany
    List<GroupMetadata> metadata = new ArrayList();;

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

    public List<GroupMetadata> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<GroupMetadata> metadata) {
        this.metadata = metadata;
    }
}
