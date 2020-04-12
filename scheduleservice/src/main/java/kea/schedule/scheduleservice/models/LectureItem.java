package kea.schedule.scheduleservice.models;

import javax.persistence.*;

@Entity(name= "LectureItem")
@Table(name= "lectureitem")
public class LectureItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String description;

}
