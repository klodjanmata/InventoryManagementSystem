package Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "Customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Customer {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;


    private String name;
    private String email;
    private String phone;

}
