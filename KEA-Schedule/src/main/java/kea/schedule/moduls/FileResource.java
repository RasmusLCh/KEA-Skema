package kea.schedule.moduls;

import javax.persistence.*;

@Entity(name= "FileResource")
@Table(name= "fileresources",
        uniqueConstraints={
                @UniqueConstraint(columnNames = {"filename", "microserviceid"})
        })
public class FileResource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="filename", columnDefinition="VARCHAR(255)")
    private String filename = null;
    @Column(name = "type")
    private String type = null;
    @Column(name = "extension")
    private String extension= null;
    @Column(name="data", columnDefinition="BLOB")
    private byte[] data = null;
    @Column(name = "microserviceid")
    private int microserviceId;

    public FileResource(){

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

    public int getMicroserviceId() {
        return microserviceId;
    }

    public void setMicroserviceId(int microserviceId) {
        this.microserviceId = microserviceId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
