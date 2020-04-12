package kea.schedule.scheduleservice.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name= "Lecture")
@Table(name= "lecture")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private LocalDateTime startdatetime;
    private LocalDateTime enddatetime;
    private String room;
    @OneToMany
    private List<User> teacher;
    @OneToMany
    private List<LectureSubject> lecturesubjects;
}
