package Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "Product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Price")
    private double price;

    @Column(name = "Stock")
    private int stock;

    @OneToOne
    private Supplier supplier;

    @OneToOne
    private Category category;

    @Override
    public String toString() {
        return STR."\{id}\t\{name}\t\{price}\t\{stock}\t\{supplier.getName()}\t\{category.getName()}";
        }

    }

