package kea.schedule.modules;

import javax.persistence.*;

@Entity(name= "TopMenuLink")
@Table(name= "topmenulinks")
public class TopMenuLink {
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
    @Column
    private int microserviceId;
    public TopMenuLink(){}

    public TopMenuLink(String path, String text, String description, int priority){
        this(path, text, description, priority,0);
    }

    public TopMenuLink(String path, String text, String description, int priority, int microserviceId){
        this(0, path, text, description, "eng", priority, microserviceId);
    }

    public TopMenuLink(int id, String path, String text, String description, String language, int priority, int microserviceId){
        this.id = id;
        this.path = path;
        this.text = text;
        this.description = description;
        this.language = language;
        this.priority = priority;
        this.microserviceId = microserviceId;
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

    public int getMicroserviceId() {
        return microserviceId;
    }

    public void setMicroserviceId(int microserviceId) {
        this.microserviceId = microserviceId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
