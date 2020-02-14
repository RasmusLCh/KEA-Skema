package proof.concept.architecture.modules;

import javax.persistence.*;

@Entity(name= "MicroService")
@Table(name= "microservices")
public class MicroService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="name", columnDefinition="VARCHAR(50)", unique = true)
    private String name = null;
    @Column(name="port", unique = true)
    private int port;
    @Column(name="enabled")
    private boolean enabled;

    public MicroService(){ }

    public MicroService(String name, int port){
        this(0, name, port);
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

    public boolean isEnabled(){ return this.enabled; }

    public void setEnabled(boolean enabled){ this.enabled = enabled; }
}
