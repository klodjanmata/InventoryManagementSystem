package Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "Sale")
@AllArgsConstructor
@NoArgsConstructor

public class Sales {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Costomer costomer;

    private LocalDate saleDate;
    private double totalAmount;
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleItem> items = new ArrayList<>();



}