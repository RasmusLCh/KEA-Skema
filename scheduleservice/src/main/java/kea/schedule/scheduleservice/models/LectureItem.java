package kea.schedule.scheduleservice.models;

import javax.persistence.*;

@Entity(name= "LectureItem")
@Table(name= "lectureitem")
public class LectureItem  implements ModelInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String description;

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

    @Override
    public int getId() {
        return 0;
    }
}
