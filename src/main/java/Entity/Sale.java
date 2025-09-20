package Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "Sale")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Sale {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Customer customer;

    @Column
    private LocalDate saleDate;

    @Column
    private double totalAmount;

    @OneToMany(mappedBy = "sale")
    private List<SaleItem> items = new ArrayList<>();



}