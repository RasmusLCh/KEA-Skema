package kea.schedule.moduls;

import javax.persistence.*;

@Entity(name= "GrouMetadata")
@Table(name= "groupmetadatas")
public class GroupMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
}
