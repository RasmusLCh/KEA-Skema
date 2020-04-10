package kea.schedule.moduls;

import net.minidev.json.JSONObject;

import javax.persistence.*;

@Entity(name= "TopMenuLink")
@Table(name= "topmenulinks")
public class TopMenuLink implements ModelInterface, MicroServiceElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String path = null;
    @Column
    private String text = null;
    @Column
    private String description = null;
    @Column(name = "language")
    private String language = "eng";
    @Column
    private int priority = 50;
    @ManyToOne(cascade= {CascadeType.ALL})
    private MicroService microservice;
    public TopMenuLink(){}

    public TopMenuLink(String path, String text, String description, int priority){
        this(path, text, description, priority,null);
    }

    public TopMenuLink(String path, String text, String description, int priority, MicroService microservice){
        this(0, path, text, description, "eng", priority, microservice);
    }

    public TopMenuLink(int id, String path, String text, String description, String language, int priority, MicroService microservice){
        this.id = id;
        this.path = path;
        this.text = text;
        this.description = description;
        this.language = language;
        this.priority = priority;
        this.microservice = microservice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public JSONObject toJSON(JSONObject obj) {
        return toJSON(obj, false);
    }

    public JSONObject toJSON(JSONObject obj, boolean recursive) {
        obj.appendField("id", getId());
        obj.appendField("text", getText());
        obj.appendField("path", getPath());
        obj.appendField("description", getDescription());
        obj.appendField("language", getLanguage());
        obj.appendField("priority", getPriority());
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
        return obj;
    }
}
