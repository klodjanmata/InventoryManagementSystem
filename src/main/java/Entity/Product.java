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
        private int id;

        private String name;
        private double price;
        private int stock;

        @ManyToOne
        @JoinColumn(name = "supplier_id")
        private Supplier supplier;

        @ManyToOne
        @JoinColumn(name = "category_id")
        private Category category;

    }

