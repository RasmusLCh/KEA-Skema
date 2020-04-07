package kea.schedule.moduls;

import net.minidev.json.JSONObject;

import javax.persistence.*;

@Entity(name= "FileResource")
@Table(name= "fileresources",
        uniqueConstraints={
                @UniqueConstraint(columnNames = {"filename", "microserviceid"})
        })
public class FileResource implements MicroServiceElement, ModelInterface {
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
    @ManyToOne
    @JoinColumn(name="microserviceid")
    private MicroService microservice;

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

    @Override
    public JSONObject toJSON(JSONObject obj) {
        return toJSON(obj, false);
    }

    @Override
    public JSONObject toJSON(JSONObject obj, boolean recursive) {
        obj.appendField("id", getId());
        obj.appendField("filename", getFilename());
        obj.appendField("type", getType());
        obj.appendField("extension", getExtension());
        if(recursive){
            if(getMicroservice() != null){
                obj.appendField("microservice", getMicroservice().toJSON(new JSONObject()));
            }
        }
        else{
            if(getMicroservice() != null){
                obj.appendField("microservice", getMicroservice().getId());
            }
        }
        //Groups
        return obj;
    }
}
