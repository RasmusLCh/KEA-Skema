package proof.concept.architecture.modules;

import javax.persistence.*;

@Entity(name= "User")
@Table(name= "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String identifier; //This is a unique user identifier
}
