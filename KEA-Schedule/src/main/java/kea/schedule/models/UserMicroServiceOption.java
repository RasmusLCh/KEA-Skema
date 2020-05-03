package kea.schedule.models;

import javax.persistence.*;

//This is the glue between users and microserviceoptions, enabling users to enable or disable microserviceoptions.
//Ref: https://vladmihalcea.com/the-best-way-to-map-a-many-to-many-association-with-extra-columns-when-using-jpa-and-hibernate/
@Entity
@Table(name = "usermicroserviceoption")
public class UserMicroServiceOption implements ModelInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(cascade= {CascadeType.DETACH})
    MicroServiceOption microserviceoption;
    @ManyToOne(cascade= {CascadeType.DETACH})
    User user;
    @Column
    String active;


    public UserMicroServiceOption(){}

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public MicroServiceOption getMicroserviceoption() {
        return microserviceoption;
    }

    public void setMicroserviceoption(MicroServiceOption microserviceoption) {
        this.microserviceoption = microserviceoption;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
