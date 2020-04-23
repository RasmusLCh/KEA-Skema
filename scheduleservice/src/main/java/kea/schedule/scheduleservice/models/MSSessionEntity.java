package kea.schedule.scheduleservice.models;

import kea.schedule.scheduleservice.converters.MapMSSessionEntity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity(name= "MSSession")
@Table(name= "mssession")
public class MSSessionEntity implements ModelInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Lob
    @Convert(converter = MapMSSessionEntity.class)
    private Map<String, Object> attributes = new HashMap<String, Object>();
    @Column
    private int userid;
    @Column
    private long expiretime;

    public MSSessionEntity(){

    }

    public MSSessionEntity(int userid){
        setUserid(userid);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public long getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(long expiretime) {
        this.expiretime = expiretime;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof MSSessionEntity){
            return ((MSSessionEntity)obj).getId() == this.id;
        }
        return false;
    }
}
