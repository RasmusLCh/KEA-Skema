package kea.schedule.scheduleservice.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.List;

@Entity(name= "Course")
@Table(name= "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToMany
    private List<Group> teachers;
    @ManyToMany
    private List<Group> students;
    @ManyToMany
    private List<Lecture> lectures;
}
