package kea.schedule.scheduleservice.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name= "Lecture")
@Table(name= "lecture")
public class Lecture implements ModelInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    //@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startdatetime;
    //@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime enddatetime;
    @Column
    private String location="";
    @OneToMany
    private List<User> teachers = new ArrayList();
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "lecture", orphanRemoval = true)
    private List<LectureSubject> lecturesubjects = new ArrayList();
    @ManyToOne(cascade= {CascadeType.DETACH})
    private Course course = null;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<User> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<User> teachers) {
        this.teachers = teachers;
    }

    public List<LectureSubject> getLecturesubjects() {
        return lecturesubjects;
    }

    public void setLecturesubjects(List<LectureSubject> lecturesubjects) {
        this.lecturesubjects = lecturesubjects;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
