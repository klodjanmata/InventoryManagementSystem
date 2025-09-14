package Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Category")
@AllArgsConstructor
@NoArgsConstructor

public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
}
