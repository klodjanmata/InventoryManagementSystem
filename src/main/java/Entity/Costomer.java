package Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "Costomer")
@AllArgsConstructor
@NoArgsConstructor

public class Costomer {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;


    private String name;
    private String email;
    private String phone;

}
