package proof.concept.modules.modules;

import javax.persistence.*;

@Entity(name= "MicroServicePageInjection")
@Table(name= "microservicepageinjections")
public class PageInjection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String servicename;
    @Column
    private String type;
    @Column
    private String page;
    @Column
    private String data;
    @Column
    private int priority;

    public PageInjection(){

    }

    public PageInjection(String servicename, String type, String page, String data){
        this(-1, servicename, type, page, data);
    }

    public PageInjection(int id, String servicename, String type, String page, String data){
        this.id = id;
        this.servicename = servicename;
        this.type = type;
        this.page = page;
        this.data = data;
        this.priority = priority;
    }
}
