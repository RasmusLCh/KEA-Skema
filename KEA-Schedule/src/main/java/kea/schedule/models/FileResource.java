package kea.schedule.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name= "FileResource")
@Table(name= "fileresources")
/*
@Table(name= "fileresources",
        uniqueConstraints={
                @UniqueConstraint(columnNames = {"filename", "microserviceid"})
        })

 */
public class FileResource implements MicroServiceElement, ModelInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="filename", columnDefinition="VARCHAR(255)")
    @Size(min=1,max=255)
    @NotNull
    private String filename = "";
    @Column(name = "type", columnDefinition="VARCHAR(50)")
    @Size(min=1,max=50)
    @NotNull
    private String type = "";
    @Column(name = "extension", columnDefinition="VARCHAR(20)")
    @Size(min=1,max=20)
    @NotNull
    private String extension= "";
    @Column(name="data", columnDefinition="BLOB")
    @JsonIgnore
    private byte[] data = null;
    @ManyToOne(cascade= {CascadeType.DETACH})
    private MicroService microservice;

    public FileResource(){

    }

    public FileResource(int id){
        setId(id);
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

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public MicroService getMicroservice() {
        return microservice;
    }

    public void setMicroservice(MicroService microservice) {
        this.microservice = microservice;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
