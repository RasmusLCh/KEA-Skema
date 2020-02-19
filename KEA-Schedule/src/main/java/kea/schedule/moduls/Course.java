package kea.schedule.moduls;

import javax.persistence.*;

@Entity(name= "Course")
@Table(name= "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

}
