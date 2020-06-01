package kea.schedule.scheduleservice.models;

import javax.persistence.*;

/**
 * Possiblity for random settings
 * */
@Entity(name = "schedulesetting")
@Table(name = "schedulesettings")
public class ScheduleSetting implements ModelInterface{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    String name = null;
    @Column
    String value = null;

    public ScheduleSetting(){}

    public ScheduleSetting(int id){
        setId(id);
    }

    public ScheduleSetting(String name, String value){
        setName(name);
        setValue(value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
