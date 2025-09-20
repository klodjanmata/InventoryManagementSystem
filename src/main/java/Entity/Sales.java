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

public class Sales {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Customer customer;

    @Column
    private LocalDate saleDate;

    @Column
    private double totalAmount;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleItem> items = new ArrayList<>();



}