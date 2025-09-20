package Service;

import Entity.Sale;
import Repository.SaleRepository;

import java.util.List;

public class SaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public void addSale(Sale sale) {
        saleRepository.save(sale);
    }

    public void updateSale(Sale sale) {
        saleRepository.update(sale);
    }

    public void deleteSale(Sale sale) {
        saleRepository.delete(sale);
    }

    public Sale getSaleById(Long id) {
        return saleRepository.findById(id);
    }

    public List<Sale> getAllSale() {
        return saleRepository.findAll();
    }
}
