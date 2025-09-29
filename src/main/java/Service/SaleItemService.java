package Service;

import Entity.Product;
import Entity.Sale;
import Entity.SaleItem;
import Repository.ProductRepository;
import Repository.SaleItemRepository;
import java.math.BigDecimal;
import java.util.List;

public class SaleItemService {

    private final SaleItemRepository saleItemRepository;
    private final ProductRepository productRepository;

    public SaleItemService(SaleItemRepository saleItemRepository, ProductRepository productRepository) {
        this.saleItemRepository = saleItemRepository;
        this.productRepository = productRepository;
    }

    // ✅ Add item to sale with stock validation
    public boolean addItemToSale(Sale sale, Product product, int quantity) {
        if (quantity <= 0 || quantity > product.getStock()) {
            System.out.println("❌ Invalid quantity. Available stock: " + product.getStock());
            return false;
        }

        SaleItem item = new SaleItem();
        item.setSale(sale);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setPrice(product.getPrice());

        sale.getItems().add(item);
        product.setStock(product.getStock() - quantity);

        productRepository.update(product);

        System.out.println("✅ Item added: " + product.getName() + " x" + quantity);
        return true;
    }

    // 🧮 Calculate subtotal
    public BigDecimal calculateSubtotal(SaleItem item) {
        return item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
    }

    // 🔍 Get items by sale ID
    public List<SaleItem> getItemsBySaleId(Long saleId) {
        return saleItemRepository.findBySaleId(saleId);
    }

    // ❌ Remove item and restore stock
    public void removeItem(Long itemId) {
        SaleItem item = saleItemRepository.findById(itemId);
        if (item != null) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.update(product);
            saleItemRepository.delete(item);
            System.out.println("🗑️ Item removed and stock restored.");
        } else {
            System.out.println("❌ Item not found.");
        }
    }
}

