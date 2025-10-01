package Service;

import Entity.*;
import Repository.*;
import Util.Helper;
import Util.Printer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

        System.out.println("✅ Sale completed."); //+ totalAmount + " EUR");
        Printer.printInvoice(sale);
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

        Printer.printInvoice(sale);
    }

//    public void printSalesByCustomer() {
//        System.out.println("🔍 View sales by customer");
//
//        customerService.printAllCustomers();
//        int customerId = Helper.getIntFromUser("Enter Customer ID");
//        Optional<Customer> customerOpt = Optional.ofNullable(customerRepository.findById(customerId));
//
//        if (customerOpt.isEmpty()) {
//            System.out.println("❌ Customer not found.");
//            return;
//        }
//
//        List<Sale> sales = saleRepository.findByCustomerId(customerId);
//        if (sales.isEmpty()) {
//            System.out.println("📭 No sales found for this customer.");
//            return;
//        }
//
//        for (Sale sale : sales) {
//            System.out.println("🧾 Sale ID: " + sale.getId() +
//                    " | Date: " + sale.getSaleDate() +
//                    " | Total: " + sale.getTotalAmount() + " EUR" +
//                    " | Items: " + sale.getItems().size());
//        }
//    }

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

        Printer.printSales(sales);

        for (Sale sale : sales) {
            Customer customer = sale.getCustomer();
            int itemCount = sale.getItems() != null ? sale.getItems().size() : 0;



//            System.out.println("🔹 Sale ID: " + sale.getId());
//            System.out.println("   👤 Customer: " + (customer != null ? customer.getId() + " - " + customer.getName() : "Unknown"));
//            System.out.println("   📅 Date: " + sale.getSaleDate());
//            System.out.println("   🧺 Items: " + itemCount);
//            System.out.println("   💰 Total: " + sale.getTotalAmount() + " EUR");
//            System.out.println("--------------------------------------------------");
        }
    }

    public void printSalesByDateRange() {
        System.out.println("📅 Filter Sales by Date Range (format: dd.MM.yyyy)");

        LocalDate start = Helper.getLocalDateFromUser("Enter start date");
        if (start == null) {
            System.out.println("⚠️ No start date provided. Aborting filter.");
            return;
        }

        LocalDate end = Helper.getLocalDateFromUser("Enter end date");
        if (end == null) {
            System.out.println("⚠️ No end date provided. Aborting filter.");
            return;
        }

        List<Sale> sales = saleRepository.findAll();

        List<Sale> filtered = sales.stream()
                .filter(s -> !s.getSaleDate().isBefore(start) && !s.getSaleDate().isAfter(end))
                .toList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (filtered.isEmpty()) {
            System.out.println("⚠️ No sales found between " + start.format(formatter) + " and " + end.format(formatter));
        } else {
            System.out.println("✅ Sales between " + start.format(formatter) + " and " + end.format(formatter) + ":");
            Printer.printSales(filtered);
        }
    }

    public void printSalesByCustomer() {
        System.out.println("👤 Filter Sales by Customer");

        customerService.printAllCustomers();
        int customerId = Helper.getIntFromUser("Enter Customer ID");

        Optional<Customer> customerOpt = Optional.ofNullable(customerRepository.findById(customerId));
        if (customerOpt.isEmpty()) {
            System.out.println("❌ Customer not found. Aborting filter.");
            return;
        }

        Customer customer = customerOpt.get();
        List<Sale> sales = saleRepository.findAll();

        List<Sale> filtered = sales.stream()
                .filter(s -> s.getCustomer() != null && s.getCustomer().getId() == customer.getId())
                .toList();

        if (filtered.isEmpty()) {
            System.out.println("⚠️ No sales found for customer: " + customer.getName());
        } else {
            System.out.println("✅ Sales for customer: " + customer.getName());
            Printer.printSales(filtered);
        }
    }

    public void printSalesByAmount() {
        System.out.println("💰 Filter Sales by Total Amount");

        double min = Helper.getDoubleFromUser("Enter minimum amount");
        double max = Helper.getDoubleFromUser("Enter maximum amount");

        if (min > max) {
            System.out.println("❌ Minimum cannot be greater than maximum. Aborting filter.");
            return;
        }

        List<Sale> sales = saleRepository.findAll();

        List<Sale> filtered = sales.stream()
                .filter(s -> {
                    double total = s.getTotalAmount().doubleValue();
                    return total >= min && total <= max;
                })
                .toList();

        if (filtered.isEmpty()) {
            System.out.println("⚠️ No sales found in range " + min + " - " + max);
        } else {
            System.out.println("✅ Sales in range " + min + " - " + max + ":");
            Printer.printSales(filtered);
        }
    }

    public void deleteSale() {
        System.out.println("🗑️ Delete Sale");

        printAllSales();
        Long saleId = Helper.getLongFromUser("Enter Sale ID to delete");

        Optional<Sale> saleOpt = Optional.ofNullable(saleRepository.findById(saleId));
        if (saleOpt.isEmpty()) {
            System.out.println("❌ Sale with ID " + saleId + " not found.");
            return;
        }

        Sale sale = saleOpt.get();

        System.out.println("Sale ID: " + sale.getId() +
                " | Date: " + sale.getSaleDate() +
                " | Total: " + sale.getTotalAmount());

        boolean confirm = Helper.getYesNoFromUser("Are you sure you want to delete this sale?");
        if (!confirm) {
            System.out.println("❎ Deletion cancelled.");
            return;
        }

        // 🔄 Restore stock for each item
        if (sale.getItems() != null) {
            for (SaleItem item : sale.getItems()) {
                Product product = item.getProduct();
                if (product != null) {
                    product.setStock(product.getStock() + item.getQuantity());
                    productRepository.update(product); // make sure your repo has update()
                }
            }
        }

        // 🗑️ Delete the sale
        saleRepository.delete(sale);
        System.out.println("✅ Sale deleted and stock restored.");
    }



}
