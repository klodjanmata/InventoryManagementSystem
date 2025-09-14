package Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "Costumer")
@AllArgsConstructor
@NoArgsConstructor

public class Costumer {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;


    private String name;
    private String email;
    private String phone;

}
