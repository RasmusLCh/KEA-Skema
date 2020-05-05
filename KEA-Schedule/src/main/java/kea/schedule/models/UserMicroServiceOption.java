package kea.schedule.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kea.schedule.converters.deserialize.ListGenericModelDeserializer;
import kea.schedule.converters.deserialize.ListGroupDeserializer;
import kea.schedule.converters.deserialize.MicroServiceOptionDeserializer;
import kea.schedule.converters.deserialize.UserDeserializer;
import kea.schedule.converters.serialize.ListGroupSerializer;
import kea.schedule.converters.serialize.ListModelInterfaceSerializer;
import kea.schedule.converters.serialize.MicroServiceOptionSerializer;
import kea.schedule.converters.serialize.UserSerializer;

import javax.persistence.*;

//This is the glue between users and microserviceoptions, enabling users to enable or disable microserviceoptions.
//Ref: https://vladmihalcea.com/the-best-way-to-map-a-many-to-many-association-with-extra-columns-when-using-jpa-and-hibernate/
@Entity
@Table(name= "usermicroserviceoption",
        uniqueConstraints={
                @UniqueConstraint(columnNames = {"microserviceoption_id", "user_id"})
        })
public class UserMicroServiceOption implements ModelInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(cascade= {CascadeType.DETACH})
    @JoinColumn(name="microserviceoption_id")
    @JsonSerialize(converter = MicroServiceOptionSerializer.class)
    @JsonDeserialize(converter = MicroServiceOptionDeserializer.class)
    MicroServiceOption microserviceoption;
    @ManyToOne(cascade= {CascadeType.DETACH})
    @JoinColumn(name="user_id")
    @JsonSerialize(converter = UserSerializer.class)
    @JsonDeserialize(converter = UserDeserializer.class)
    User user;
    @Column
    boolean active;


    public UserMicroServiceOption(){}

    public UserMicroServiceOption(int id){
        setId(id);
    }

    public UserMicroServiceOption(MicroServiceOption mso, User user){
        setActive(mso.getActive());
        setUser(user);
        setMicroserviceoption(mso);
    }

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

    public boolean isActive() {
        return active;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
