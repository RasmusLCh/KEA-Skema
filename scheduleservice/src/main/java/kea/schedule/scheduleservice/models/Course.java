package kea.schedule.scheduleservice.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.List;

@Entity(name= "Course")
@Table(name= "course")
public class Course implements ModelInterface{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String name;
    @ManyToMany
    private List<Group> teachers;
    @ManyToMany
    private List<Group> students;
    @ManyToMany
    private List<Lecture> lectures;

    public Course(){}

    public Course(int id){
        setId(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Group> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Group> teachers) {
        this.teachers = teachers;
    }

    public List<Group> getStudents() {
        return students;
    }

    public void setStudents(List<Group> students) {
        this.students = students;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return 0;
    }
}
