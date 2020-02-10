package proof.concept.modules.modules;

import javax.persistence.*;

@Entity(name= "MicroService")
@Table(name= "microservices")
public class MicroService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="name", columnDefinition="VARCHAR(50)", unique = true)
    private String name;
    @Column(name="port", unique = true)
    private int port;

    public MicroService(){
        this(null, -1);
    }

    public MicroService(String name, int port){
        this(-1, name, port);
    }

    public MicroService(int id, String name, int port){
        this.id = id;
        this.name = name;
        this.port = port;
    }

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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
