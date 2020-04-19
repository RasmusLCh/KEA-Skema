package kea.schedule.scheduleservice.models;

import javax.persistence.*;
import javax.security.auth.Subject;
import java.util.List;

@Entity(name= "LectureSubject")
@Table(name= "lecturesubject")
public class LectureSubject  implements ModelInterface{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String subject;
    @OneToMany
    private List<LectureItem> items;
    @ManyToOne
    private SubjectPriority priority;
    @ManyToOne
    private Lecture lecture;

    public LectureSubject(){}

    public LectureSubject(int id){
        setId(id);
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

    public List<LectureItem> getItems() {
        return items;
    }

    public void setItems(List<LectureItem> items) {
        this.items = items;
    }

    public SubjectPriority getPriority() {
        return priority;
    }

    public void setPriority(SubjectPriority priority) {
        this.priority = priority;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
