package kea.schedule.scheduleservice.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kea.schedule.scheduleservice.converters.deserialize.LectureSubjectDeserializer;
import kea.schedule.scheduleservice.converters.deserialize.ListGroupDeserializer;
import kea.schedule.scheduleservice.converters.serialize.LectureSubjectSerializer;
import kea.schedule.scheduleservice.converters.serialize.ListGroupSerializer;

import javax.persistence.*;

@Entity(name= "LectureItem")
@Table(name= "lectureitem")
public class LectureItem  implements ModelInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String description;
    private int priority = 50;
    @ManyToOne(cascade= {CascadeType.DETACH})
    @JsonSerialize(converter = LectureSubjectSerializer.class)
    @JsonDeserialize(converter = LectureSubjectDeserializer.class)
    private LectureSubject lecturesubject;

    public LectureItem(){}

    public LectureItem(int id){
        setId(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LectureSubject getLecturesubject() {
        return lecturesubject;
    }

    public void setLecturesubject(LectureSubject lecturesubject) {
        this.lecturesubject = lecturesubject;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof LectureItem){
            return ((LectureItem)obj).getId() == this.id;
        }
        return false;
    }
}
