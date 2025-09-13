import jakarta.persistence.*;
import jdk.jfr.Category;

import java.util.function.Supplier;
@Entity
@Table (name = "Product")

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

