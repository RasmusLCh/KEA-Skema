package kea.schedule.scheduleservice.models;

import javax.persistence.*;

@Entity(name= "SubjectPriority")
@Table(name= "subjectpriority")
public class SubjectPriority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String subject = "";
    private int priority = 50;
}
