package Service;

import Entity.SaleItem;
import Repository.SaleItemRepository;

import java.util.List;

public class SaleItemService {

    private final SaleItemRepository saleItemRepository;

    public SaleItemService(SaleItemRepository saleItemRepository) {
        this.saleItemRepository = saleItemRepository;
    }

    public void addSaleItem(SaleItem saleItem) {
        saleItemRepository.save(saleItem);
    }

    public void updateSaleItem(SaleItem saleItem) {
        saleItemRepository.update(saleItem);
    }

    public void deleteSaleItem(SaleItem saleItem) {
        saleItemRepository.delete(saleItem);
    }

    public SaleItem getSaleItemById(Long id) {
        return saleItemRepository.findById(id);
    }

    public List<SaleItem> getAllSaleItems() {
        return saleItemRepository.findAll();
    }
}
