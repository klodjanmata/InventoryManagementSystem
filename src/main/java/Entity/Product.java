package Entity;

import jakarta.persistence.*;
@Entity
@Table (name = "Entity.Product")

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

