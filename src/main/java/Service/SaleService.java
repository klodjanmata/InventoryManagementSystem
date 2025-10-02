package Service;

import Entity.*;
import Repository.*;
import Util.Helper;
import Util.Printer;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class SaleService {

    private ProductRepository productRepository = new ProductRepository();
    private CustomerService customerService = new CustomerService();
    private CustomerRepository customerRepository = new CustomerRepository();
    private ProductService productService = new ProductService();
    private CategoryRepository categoryRepository = new CategoryRepository();
    private CategoryService categoryService = new CategoryService();
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

    public void printAllSales() {
        System.out.println("📦 All Sales Overview");

        List<Sale> sales = saleRepository.findAll();
        if (sales.isEmpty()) {
            System.out.println("❌ No sales found.");
            return;
        }

        Printer.printSales(sales);

//        for (Sale sale : sales) {
//            Customer customer = sale.getCustomer();
//            int itemCount = sale.getItems() != null ? sale.getItems().size() : 0;
//
//
//
////            System.out.println("🔹 Sale ID: " + sale.getId());
////            System.out.println("   👤 Customer: " + (customer != null ? customer.getId() + " - " + customer.getName() : "Unknown"));
////            System.out.println("   📅 Date: " + sale.getSaleDate());
////            System.out.println("   🧺 Items: " + itemCount);
////            System.out.println("   💰 Total: " + sale.getTotalAmount() + " EUR");
////            System.out.println("--------------------------------------------------");
//        }
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
                    productRepository.update(product);
                }
            }
        }

        // 🗑️ Delete the sale
        saleRepository.delete(sale);
        System.out.println("✅ Sale deleted and stock restored.");
    }

    public void printSalesByCategory() {
        System.out.println("🏷️ Filter Sales by Product Category");

        // 1. Show all categories
        categoryService.printAllCategories();
        Long categoryId = Helper.getLongFromUser("Enter Category ID");

        Optional<Category> categoryOpt = Optional.ofNullable(categoryRepository.findById(categoryId));
        if (categoryOpt.isEmpty()) {
            System.out.println("❌ Category not found. Aborting filter.");
            return;
        }

        Category category = categoryOpt.get();

        // 2. Get all sales
        List<Sale> sales = saleRepository.findAll();

        // 3. Filter sales that contain at least one product from this category
        List<Sale> filtered = sales.stream()
                .filter(sale -> sale.getItems() != null &&
                        sale.getItems().stream()
                                .anyMatch(item -> item.getProduct() != null &&
                                        item.getProduct().getCategory() != null &&
                                        item.getProduct().getCategory().getId() == category.getId()))
                .toList();

        // 4. Print results
        if (filtered.isEmpty()) {
            System.out.println("⚠️ No sales found for category: " + category.getName());
        } else {
            System.out.println("✅ Sales for category: " + category.getName());
            Printer.printSales(filtered);
        }
    }

    public void printSalesByProduct() {
        System.out.println("🔍 Filter Sales by Specific Product");

        // 1. Show all products so user can choose
        productService.printAllProducts();
        int productId = Helper.getIntFromUser("Enter Product ID");

        Optional<Product> productOpt = Optional.ofNullable(productRepository.findById(productId));
        if (productOpt.isEmpty()) {
            System.out.println("❌ Product not found. Aborting filter.");
            return;
        }

        Product product = productOpt.get();

        // 2. Get all sales
        List<Sale> sales = saleRepository.findAll();

        // 3. Filter sales that contain this product
        List<Sale> filtered = sales.stream()
                .filter(sale -> sale.getItems() != null &&
                        sale.getItems().stream()
                                .anyMatch(item -> item.getProduct() != null &&
                                        item.getProduct().getId() == product.getId()))
                .toList();

        // 4. Print results
        if (filtered.isEmpty()) {
            System.out.println("⚠️ No sales found for product: " + product.getName());
        } else {
            System.out.println("✅ Sales containing product: " + product.getName());
            Printer.printSales(filtered);
        }
    }

    public void printSalesByCustomerAndAmountRange() {
        System.out.println("👤💰 Filter Sales by Customer and Amount Range");

        // 1. Show all customers
        customerService.printAllCustomers();
        int customerId = Helper.getIntFromUser("Enter Customer ID");

        Optional<Customer> customerOpt = Optional.ofNullable(customerRepository.findById(customerId));
        if (customerOpt.isEmpty()) {
            System.out.println("❌ Customer not found. Aborting filter.");
            return;
        }

        Customer customer = customerOpt.get();

        // 2. Ask for amount range
        double min = Helper.getDoubleFromUser("Enter minimum amount");
        double max = Helper.getDoubleFromUser("Enter maximum amount");

        if (min > max) {
            System.out.println("❌ Minimum cannot be greater than maximum. Aborting filter.");
            return;
        }

        // 3. Get all sales
        List<Sale> sales = saleRepository.findAll();

        // 4. Filter by customer AND amount range
        List<Sale> filtered = sales.stream()
                .filter(s -> s.getCustomer() != null && s.getCustomer().getId() == customer.getId())
                .filter(s -> {
                    double total = s.getTotalAmount().doubleValue();
                    return total >= min && total <= max;
                })
                .toList();

        // 5. Print results
        if (filtered.isEmpty()) {
            System.out.println("⚠️ No sales found for customer " + customer.getName() +
                    " in range " + min + " - " + max);
        } else {
            System.out.println("✅ Sales for customer " + customer.getName() +
                    " in range " + min + " - " + max + ":");
            Printer.printSales(filtered);
        }
    }

    public void printTop5CustomersBySalesVolume() {
        System.out.println("🏆 Top 5 Customers by Sales Volume (including ties)");

        // 1. Get all sales
        List<Sale> sales = saleRepository.findAll();
        if (sales.isEmpty()) {
            System.out.println("❌ No sales found.");
            return;
        }

        // 2. Group sales by customer and sum total amounts
        Map<Customer, BigDecimal> totalsByCustomer = sales.stream()
                .filter(s -> s.getCustomer() != null)
                .collect(Collectors.groupingBy(
                        Sale::getCustomer,
                        Collectors.mapping(Sale::getTotalAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));

        // 3. Sort customers by total amount descending
        List<Map.Entry<Customer, BigDecimal>> sorted = totalsByCustomer.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .toList();

        if (sorted.isEmpty()) {
            System.out.println("⚠️ No customers with sales found.");
            return;
        }

        // 4. Determine cutoff (5th place value)
        int limit = Math.min(5, sorted.size());
        BigDecimal cutoffValue = sorted.get(limit - 1).getValue();

        // 5. Include all customers with sales >= cutoffValue
        List<Map.Entry<Customer, BigDecimal>> topWithTies = sorted.stream()
                .filter(e -> e.getValue().compareTo(cutoffValue) >= 0)
                .toList();

        // 6. Print results
        int rank = 1;
        for (Map.Entry<Customer, BigDecimal> entry : topWithTies) {
            Customer customer = entry.getKey();
            BigDecimal total = entry.getValue();
            System.out.println(rank + ". 👤 " + customer.getName() +
                    " | Total Sales: " + total + " EUR");
            rank++;
        }
    }

    public void printTop5ProductsBySalesVolume() {
        System.out.println("🏆 Top 5 Products by Sales Volume (including ties)");

        // 1. Get all sales
        List<Sale> sales = saleRepository.findAll();
        if (sales.isEmpty()) {
            System.out.println("❌ No sales found.");
            return;
        }

        // 2. Flatten all sale items
        List<SaleItem> allItems = sales.stream()
                .filter(s -> s.getItems() != null)
                .flatMap(s -> s.getItems().stream())
                .toList();

        if (allItems.isEmpty()) {
            System.out.println("⚠️ No sale items found.");
            return;
        }

        // 3. Group by product and sum quantities
        Map<Product, Integer> totalsByProduct = allItems.stream()
                .filter(item -> item.getProduct() != null)
                .collect(Collectors.groupingBy(
                        SaleItem::getProduct,
                        Collectors.summingInt(SaleItem::getQuantity)
                ));

        // 4. Sort products by total quantity sold (descending)
        List<Map.Entry<Product, Integer>> sorted = totalsByProduct.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .toList();

        if (sorted.isEmpty()) {
            System.out.println("⚠️ No products found.");
            return;
        }

        // 5. Determine cutoff (5th place value)
        int limit = Math.min(5, sorted.size());
        int cutoffValue = sorted.get(limit - 1).getValue();

        // 6. Include all products with sales >= cutoffValue
        List<Map.Entry<Product, Integer>> topWithTies = sorted.stream()
                .filter(e -> e.getValue() >= cutoffValue)
                .toList();

        // 7. Print results
        int rank = 1;
        for (Map.Entry<Product, Integer> entry : topWithTies) {
            Product product = entry.getKey();
            int totalQty = entry.getValue();
            System.out.println(rank + ". 📦 " + product.getName() +
                    " | Total Units Sold: " + totalQty);
            rank++;
        }
    }

    public void printMostFrequentSaleDays() {
        System.out.println("📅 Smart Filter: Most Frequent Sale Days");

        // 1. Get all sales
        List<Sale> sales = saleRepository.findAll();
        if (sales.isEmpty()) {
            System.out.println("❌ No sales found.");
            return;
        }

        // 2. Group by day of week and count
        Map<DayOfWeek, Long> salesByDay = sales.stream()
                .filter(s -> s.getSaleDate() != null)
                .collect(Collectors.groupingBy(
                        s -> s.getSaleDate().getDayOfWeek(),
                        Collectors.counting()
                ));

        // 3. Sort by frequency (descending)
        List<Map.Entry<DayOfWeek, Long>> sorted = salesByDay.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .toList();

        // 4. Find cutoff (most frequent value)
        long maxCount = sorted.get(0).getValue();

        // 5. Include all days with frequency == maxCount
        List<Map.Entry<DayOfWeek, Long>> topDays = sorted.stream()
                .filter(e -> e.getValue() == maxCount)
                .toList();

        // 6. Print results
        System.out.println("🏆 Most Frequent Sale Day(s):");
        for (Map.Entry<DayOfWeek, Long> entry : topDays) {
            String dayName = entry.getKey().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            System.out.println("📅 " + dayName + " → " + entry.getValue() + " sales");
        }

        // 7. Print full ranking (optional)
        System.out.println("\n📊 Full Ranking of Days:");
        for (Map.Entry<DayOfWeek, Long> entry : sorted) {
            String dayName = entry.getKey().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            System.out.println(dayName + " → " + entry.getValue() + " sales");
        }
    }



}
