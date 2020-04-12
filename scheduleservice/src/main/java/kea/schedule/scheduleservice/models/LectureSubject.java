package kea.schedule.scheduleservice.models;

import javax.persistence.*;
import java.util.List;

@Entity(name= "LectureSubject")
@Table(name= "lecturesubject")
public class LectureSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String subject;
    @OneToMany
    private List<LectureItem> items;
}
