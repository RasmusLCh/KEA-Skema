package kea.schedule.scheduleservice.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kea.schedule.scheduleservice.converters.deserialize.LectureSubjectDeserializer;
import kea.schedule.scheduleservice.converters.deserialize.ListGroupDeserializer;
import kea.schedule.scheduleservice.converters.serialize.LectureSubjectSerializer;
import kea.schedule.scheduleservice.converters.serialize.ListGroupSerializer;

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
    @JsonSerialize(converter = ListGroupSerializer.class)
    @JsonDeserialize(converter = ListGroupDeserializer.class)
    @OrderBy("name ASC")
    private List<Group> teachers;
    @ManyToMany
    @JsonSerialize(converter = ListGroupSerializer.class)
    @JsonDeserialize(converter = ListGroupDeserializer.class)
    @OrderBy("name ASC")
    private List<Group> students;
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "course", orphanRemoval = true)
    @OrderBy("startdatetime ASC")
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
        this.lectures.clear();
        this.lectures.addAll(lectures);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Course){
            return ((Course)obj).getId() == this.id;
        }
        return false;
    }
}
