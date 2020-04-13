package kea.schedule.scheduleservice.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name= "Lecture")
@Table(name= "lecture")
public class Lecture implements ModelInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private LocalDateTime startdatetime;
    private LocalDateTime enddatetime;
    private String room="?";
    @OneToMany
    private List<User> teacher;
    @OneToMany
    private List<LectureSubject> lecturesubjects;

    public Lecture(){}

    public Lecture(int id){
        setId(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStartdatetime() {
        return startdatetime;
    }

    public void setStartdatetime(LocalDateTime startdatetime) {
        this.startdatetime = startdatetime;
    }

    public LocalDateTime getEnddatetime() {
        return enddatetime;
    }

    public void setEnddatetime(LocalDateTime enddatetime) {
        this.enddatetime = enddatetime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<User> getTeacher() {
        return teacher;
    }

    public void setTeacher(List<User> teacher) {
        this.teacher = teacher;
    }

    public List<LectureSubject> getLecturesubjects() {
        return lecturesubjects;
    }

    public void setLecturesubjects(List<LectureSubject> lecturesubjects) {
        this.lecturesubjects = lecturesubjects;
    }

    @Override
    public int getId() {
        return 0;
    }
}
