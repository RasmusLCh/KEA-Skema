package kea.schedule.scheduleservice.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kea.schedule.scheduleservice.converters.deserialize.LectureDeserializer;
import kea.schedule.scheduleservice.converters.deserialize.LectureItemDeserializer;
import kea.schedule.scheduleservice.converters.deserialize.LectureSubjectDeserializer;
import kea.schedule.scheduleservice.converters.serialize.LectureItemSerializer;
import kea.schedule.scheduleservice.converters.serialize.LectureSerializer;
import kea.schedule.scheduleservice.converters.serialize.LectureSubjectSerializer;

import javax.persistence.*;
import java.util.List;

@Entity(name= "LectureSubject")
@Table(name= "lecturesubject")
public class LectureSubject  implements ModelInterface{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String subject;
    @Column
    private int priority = 50;
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "lecturesubject", orphanRemoval = true)
    private List<LectureItem> lectureitems;
    @ManyToOne(cascade= {CascadeType.DETACH})
    @JsonSerialize(converter = LectureSerializer.class)
    @JsonDeserialize(converter = LectureDeserializer.class)
    private Lecture lecture;

    public LectureSubject(){}

    public LectureSubject(int id){
        setId(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<LectureItem> getLectureitems() {
        return lectureitems;
    }

    public void setLectureitems(List<LectureItem> lectureitems) {
        this.lectureitems = lectureitems;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof LectureSubject){
            return ((LectureSubject)obj).getId() == this.id;
        }
        return false;
    }
}
