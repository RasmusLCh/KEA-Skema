package kea.schedule.scheduleservice.models;

import javax.persistence.*;

@Entity(name= "LectureItem")
@Table(name= "lectureitem")
public class LectureItem  implements ModelInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String description;
    @ManyToOne
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

    @Override
    public int getId() {
        return this.id;
    }
}
