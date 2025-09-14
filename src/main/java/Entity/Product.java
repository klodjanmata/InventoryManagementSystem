package Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "Product")
@AllArgsConstructor
@NoArgsConstructor

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

