package kea.schedule.scheduleservice.models;

import javax.persistence.*;

@Entity(name= "LectureItem")
@Table(name= "lectureitem")
public class LectureItem  implements ModelInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String description;
    private int priority = 50;
    @ManyToOne(cascade= {CascadeType.DETACH})
    private LectureSubject lecturesubject;

    public LectureItem(){}

    public LectureItem(int id){
        setId(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LectureSubject getLecturesubject() {
        return lecturesubject;
    }

    public void setLecturesubject(LectureSubject lecturesubject) {
        this.lecturesubject = lecturesubject;
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
