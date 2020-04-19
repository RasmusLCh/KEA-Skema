package kea.schedule.scheduleservice.models;

import javax.persistence.*;

@Entity(name= "SubjectPriority")
@Table(name= "subjectpriority")
public class SubjectPriority  implements ModelInterface{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String subject = "";
    private int priority = 50;

    public SubjectPriority(){
    }

    public SubjectPriority(int id){
        this(id, "", 50);
    }

    public SubjectPriority(int id, String subject, int priority){
        setId(id);
        setSubject(subject);
        setPriority(priority);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
