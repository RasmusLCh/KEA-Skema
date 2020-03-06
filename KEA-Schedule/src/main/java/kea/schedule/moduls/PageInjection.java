package kea.schedule.moduls;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name= "PageInjection")
@Table(name= "pageinjections")
public class PageInjection implements ModelInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "type", columnDefinition="VARCHAR(5)")
    @Size(min=1,max=5)
    @NotNull
    private String type = null;
    @Column(name = "page", columnDefinition="VARCHAR(200)")
    @Size(max=200)
    private String page = null;
    @Column(name = "data", columnDefinition="VARCHAR(5000)")
    @Size(max=5000)
    private String data = null;
    @Column
    @Min(0)
    @Max(1000)
    private int priority = 50;
    @ManyToOne
    private MicroService microservice;

    public PageInjection(){

    }

    public PageInjection(String type, String page, String data){
        this(type, page, data, 50);
    }

    public PageInjection(String type, String page, String data, int priority){
        this(0, type, page, data, priority, null);
    }

    public PageInjection(int id, String type, String page, String data, int priority, MicroService microservice){
        this.id = id;
        this.type = type;
        this.page = page;
        this.data = data;
        this.priority = priority;
        this.microservice = microservice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public MicroService getMicroservice() {
        return microservice;
    }

    public void setMicroservice(MicroService microservice) {
        this.microservice = microservice;
    }
}
