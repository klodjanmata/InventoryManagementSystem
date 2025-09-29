package Service;

import Entity.*;
import Repository.*;
import Util.Helper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SaleService {

    private ProductRepository productRepository = new ProductRepository();
    private CustomerService customerService = new CustomerService();
    private CustomerRepository customerRepository = new CustomerRepository();
    private ProductService productService = new ProductService();
    private final SaleRepository saleRepository = new SaleRepository();
    private final SaleItemService saleItemService = new SaleItemService(new SaleItemRepository(), productRepository);


    public void makeSale() {
        System.out.println("🛒 Start a new sale");

        // 1. Select customer
        customerService.printAllCustomers();
        int customerId = Helper.getIntFromUser("Enter Customer ID");
        Optional<Customer> customerOpt = Optional.ofNullable(customerRepository.findById(customerId));
        if (customerOpt.isEmpty()) {
            System.out.println("❌ Customer not found. Sale aborted.");
            return;
        }

        // 2. Create sale
        Sale sale = new Sale();
        sale.setCustomer(customerOpt.get());
        sale.setSaleDate(LocalDate.now());

        BigDecimal totalAmount = BigDecimal.ZERO;

        // 3. Add products to sale
        while (true) {
            productService.printAllProducts();
            int productId = Helper.getIntFromUser("Enter Product ID to add (0 to finish)");
            if (productId == 0) break;

            Optional<Product> productOpt = Optional.ofNullable(productRepository.findById(productId));
            if (productOpt.isEmpty()) {
                System.out.println("⚠️ Product not found. Skipping.");
                continue;
            }

            Product product = productOpt.get();
            int quantity = Helper.getIntFromUser("Enter quantity");
            if (quantity <= 0 || quantity > product.getStock()) {
                System.out.println("⚠️ Invalid quantity. Available stock: " + product.getStock());
                continue;
            }

            boolean added = saleItemService.addItemToSale(sale, product, quantity);
            if (added) {
                BigDecimal price = product.getPrice();
                totalAmount = totalAmount.add(price.multiply(BigDecimal.valueOf(quantity)));
            }
        }

        // 4. Validate and save
        if (sale.getItems().isEmpty()) {
            System.out.println("❌ No items added. Sale aborted.");
            return;
        }

        sale.setTotalAmount(totalAmount);
        saleRepository.save(sale);

        System.out.println("✅ Sale completed. Total: " + totalAmount + " EUR");
    }

    public void printSaleDetails() {
        System.out.println("📋 View sale details");
        printAllSales();

        Long saleId = Helper.getLongFromUser("Enter Sale ID");
        Sale sale = saleRepository.findById(saleId);

        if (sale == null) {
            System.out.println("❌ Sale not found.");
            return;
        }

        printInvoice(sale);
    }

    public void printSalesByCustomer() {
        System.out.println("🔍 View sales by customer");

        customerService.printAllCustomers();
        int customerId = Helper.getIntFromUser("Enter Customer ID");
        Optional<Customer> customerOpt = Optional.ofNullable(customerRepository.findById(customerId));

        if (customerOpt.isEmpty()) {
            System.out.println("❌ Customer not found.");
            return;
        }

        List<Sale> sales = saleRepository.findByCustomerId(customerId);
        if (sales.isEmpty()) {
            System.out.println("📭 No sales found for this customer.");
            return;
        }

        for (Sale sale : sales) {
            System.out.println("🧾 Sale ID: " + sale.getId() +
                    " | Date: " + sale.getSaleDate() +
                    " | Total: " + sale.getTotalAmount() + " EUR" +
                    " | Items: " + sale.getItems().size());
        }
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

    public void printAllSales() {
        System.out.println("📦 All Sales Overview");

        List<Sale> sales = saleRepository.findAll();
        if (sales.isEmpty()) {
            System.out.println("❌ No sales found.");
            return;
        }

        for (Sale sale : sales) {
            Customer customer = sale.getCustomer();
            int itemCount = sale.getItems() != null ? sale.getItems().size() : 0;

            System.out.println("🔹 Sale ID: " + sale.getId());
            System.out.println("   👤 Customer: " + (customer != null ? customer.getId() + " - " + customer.getName() : "Unknown"));
            System.out.println("   📅 Date: " + sale.getSaleDate());
            System.out.println("   🧺 Items: " + itemCount);
            System.out.println("   💰 Total: " + sale.getTotalAmount() + " EUR");
            System.out.println("--------------------------------------------------");
        }
    }

    private void printInvoice(Sale sale) {
        Customer customer = sale.getCustomer();

        System.out.println("===============================================");
        System.out.println("           📦 TECH SHOP INVOICE 📦             ");
        System.out.println("===============================================");
        System.out.println("🧾 Sale ID     : " + sale.getId());
        System.out.println("👤 Customer    : " + (customer != null ? customer.getId() + " - " + customer.getName() : "Unknown"));
        System.out.println("📅 Date        : " + sale.getSaleDate());
        System.out.println("-----------------------------------------------");
        System.out.println("🧺 Items:");
        System.out.printf("   %-20s %5s %10s %12s%n", "Product", "Qty", "Unit Price", "Subtotal");

        List<SaleItem> items = sale.getItems();
        if (items == null || items.isEmpty()) {
            System.out.println("   No items in this sale.");
        } else {
            for (SaleItem item : items) {
                Product product = item.getProduct();
                BigDecimal subtotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                System.out.printf("   %-20s %5d %10.2f %12.2f%n",
                        product.getName(),
                        item.getQuantity(),
                        item.getPrice(),
                        subtotal);
            }
        }

        System.out.println("-----------------------------------------------");
        System.out.printf("💰 TOTAL AMOUNT: %38.2f EUR%n", sale.getTotalAmount());
        System.out.println("===============================================");
        System.out.println("         ✅ Thank you for your purchase!        ");
        System.out.println("===============================================");
    }
}
