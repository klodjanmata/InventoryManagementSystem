package Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Role")
    private String role;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Override
    public String toString() {
        return id + "\t" + name + "\t" + role +  "\t" + hireDate;
    }
}
