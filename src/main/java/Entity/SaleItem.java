package Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Sale_id")
    private Sales sales;

    @ManyToOne
    @JoinColumn(name = "Product_id")
    private Product product;

    private Integer quantity;
    private BigDecimal price;
}
