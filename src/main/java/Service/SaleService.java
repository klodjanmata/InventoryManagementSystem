package Service;

import Entity.Sales;
import Repository.SaleRepository;

import java.util.List;

public class SaleService {

    private final SaleRepository salesRepository;

    public SaleService(SaleRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    public void addSales(Sales sales) {
        salesRepository.save(sales);
    }

    public void updateSales(Sales sales) {
        salesRepository.update(sales);
    }

    public void deleteSales(Sales sales) {
        salesRepository.delete(sales);
    }

    public Sales getSalesById(Long id) {
        return salesRepository.findById(id);
    }

    public List<Sales> getAllSales() {
        return salesRepository.findAll();
    }
}
