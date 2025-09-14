package Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Employees")
public class Employess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String role;

    @Column(name = "hire_date")
    private LocalDate hireDate;
}
