package Service;

import Entity.*;
import Repository.CategoryRepository;
import Repository.ProductRepository;
import Repository.SaleRepository;
import Repository.SupplierRepository;
import Util.Helper;
import Util.Printer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductService {

    private ProductRepository productRepository = new ProductRepository();
    private SupplierRepository supplierRepository = new SupplierRepository();
    private SupplierService supplierService = new SupplierService();
    private CategoryRepository categoryRepository = new CategoryRepository();
    private CategoryService categoryService = new CategoryService();
    private SaleRepository saleRepository = new SaleRepository();

    public ProductService() {
        this.productRepository = productRepository;
    }

    public void addProduct() {
        Product product = new Product();
        System.out.println("📦 Provide product details");

        // Basic product info
        product.setName(Helper.getStringFromUser("Enter Product Name"));
        product.setPrice(Helper.getBigDecimalFromUser("Enter Product Price"));
        product.setStock(Helper.getIntFromUser("Enter Product Stock"));

        // Supplier selection
        supplierService.printAllSuppliers();
        int supplierId = Helper.getIntFromUser("Enter Supplier ID");
        Optional<Supplier> supplierOpt = Optional.ofNullable(supplierRepository.findById(supplierId));
        if (supplierOpt.isPresent()) {
            product.setSupplier(supplierOpt.get());
        } else {
            System.out.println("❌ Supplier not found. Product not added.");
            return;
        }

        // Category selection
        categoryService.printAllCategories();
        Long categoryId = Helper.getLongFromUser("Enter Category ID");
        Optional<Category> categoryOpt = Optional.ofNullable(categoryRepository.findById(categoryId));
        if (categoryOpt.isPresent()) {
            product.setCategory(categoryOpt.get());
        } else {
            System.out.println("❌ Category not found. Product not added.");
            return;
        }

        // Save product
        productRepository.save(product);
        System.out.println("✅ Product added: " + product.getName());
    }

    public void updateProduct() {
        System.out.println("🔄 Update product details");

        printAllProducts();
        int productId = Helper.getIntFromUser("Enter Product ID to update");
        Optional<Product> productOpt = Optional.ofNullable(productRepository.findById(productId));

        if (productOpt.isEmpty()) {
            System.out.println("❌ Product not found. Update aborted.");
            return;
        }

        Product product = productOpt.get();

        // Show current values and ask for updates
        System.out.println("Current Name: " + product.getName());
        String newName = Helper.getStringFromUser("Enter new name (leave blank to keep current)");
        if (!newName.isBlank()) product.setName(newName);

        System.out.println("Current Price: " + product.getPrice());
        BigDecimal newPrice = Helper.getBigDecimalFromUser("Enter new price (-1 to keep current)");
        if (newPrice.compareTo(BigDecimal.valueOf(-1)) > 0) {
            product.setPrice(newPrice);
        }


        System.out.println("Current Stock: " + product.getStock());
        int newStock = Helper.getIntFromUser("Enter new stock (-1 to keep current)");
        if (newStock >= 0) product.setStock(newStock);

        // Update supplier
        supplierService.printAllSuppliers();
        Supplier currentSupplier = product.getSupplier();
        System.out.println(STR."Current Supplier: \{currentSupplier != null
                ? STR."\{currentSupplier.getId()} - \{currentSupplier.getName()}"
                : "None"}");

        int supplierId = Helper.getIntFromUser("Enter new Supplier ID (0 to keep current)");
        if (supplierId > 0) {
            Optional<Supplier> supplierOpt = Optional.ofNullable(supplierRepository.findById(supplierId));
            if (supplierOpt.isPresent()) {
                product.setSupplier(supplierOpt.get());
            } else {
                System.out.println("⚠️ Supplier not found. Keeping current supplier.");
            }
        }

        // Update category
        categoryService.printAllCategories();
        Category currentCategory = product.getCategory();
        System.out.println(STR."Current Category: \{currentCategory != null
                ? STR."\{currentCategory.getId()} - \{currentCategory.getName()}"
                : "None"}");

        Long categoryId = Helper.getLongFromUser("Enter new Category ID (0 to keep current)");
        if (categoryId > 0) {
            Optional<Category> categoryOpt = Optional.ofNullable(categoryRepository.findById(categoryId));
            if (categoryOpt.isPresent()) {
                product.setCategory(categoryOpt.get());
            } else {
                System.out.println("⚠️ Category not found. Keeping current category.");
            }
        }

        productRepository.update(product);
        System.out.println("✅ Product updated successfully.");
    }

    public void deleteProduct() {
        System.out.println("⚠️ Delete a product");
        printAllProducts();

        int productId = Helper.getIntFromUser("Enter Product ID to delete");
        Optional<Product> productOpt = Optional.ofNullable(productRepository.findById(productId));

        if (productOpt.isEmpty()) {
            System.out.println("❌ Product not found. Deletion aborted.");
            return;
        }

        Product product = productOpt.get();
        printCurrentProductDetails(product);

        String confirmation = Helper.getStringFromUser("Type 'YES' to confirm deletion or any other character to cancel");
        if (confirmation.equalsIgnoreCase("YES")) {
            productRepository.delete(product);
            System.out.println("✅ Product deleted successfully.");
        } else {
            System.out.println("❎ Deletion cancelled.");
        }
    }

    public void updateProductStockIncremental() {
        System.out.println("📦 Update Product Stock (Increment/Decrement)");

        // 1. Show all products so user can choose
        printAllProducts();
        int productId = Helper.getIntFromUser("Enter Product ID");

        Optional<Product> productOpt = Optional.ofNullable(productRepository.findById(productId));
        if (productOpt.isEmpty()) {
            System.out.println("❌ Product not found.");
            return;
        }

        Product product = productOpt.get();

        // 2. Ask for stock adjustment (can be positive or negative)
        int adjustment = Helper.getIntFromUser("Enter stock adjustment (e.g., +10 or -5)");

        int currentStock = product.getStock();
        int newStock = currentStock + adjustment;

        if (newStock < 0) {
            System.out.println("❌ Stock cannot go below 0. Current stock: " + currentStock);
            return;
        }

        // 3. Update stock
        product.setStock(newStock);
        productRepository.update(product);

        // 4. Confirm update
        System.out.println("✅ Stock updated for product: " + product.getName() +
                " | Previous Stock: " + currentStock +
                " | Adjustment: " + adjustment +
                " | New Stock: " + newStock);
    }

    public void printAllProducts() {
        List<Product> products = productRepository.findAll();
        Printer.printProducts(products);
    }

    public void printCurrentProductDetails(Product product) {
        System.out.println("📝 Current Product Details:");
        System.out.println("Name: " + product.getName());
        System.out.println("Price: " + product.getPrice());
        System.out.println("Stock: " + product.getStock());

        Supplier supplier = product.getSupplier();
        if (supplier != null) {
            System.out.println("Supplier: " + supplier.getId() + " - " + supplier.getName());
        } else {
            System.out.println("Supplier: None");
        }

        Category category = product.getCategory();
        if (category != null) {
            System.out.println("Category: " + category.getId() + " - " + category.getName());
        } else {
            System.out.println("Category: None");
        }
    }

    public void printProductsByCategory() {
        System.out.println("🔎 Filter Products by Category");

        // Show all categories first
        categoryService.printAllCategories();
        Long categoryId = Helper.getLongFromUser("Enter Category ID to filter products");

        Optional<Category> categoryOpt = Optional.ofNullable(categoryRepository.findById(categoryId));
        if (categoryOpt.isEmpty()) {
            System.out.println("❌ Category not found. Aborting filter.");
            return;
        }

        Category category = categoryOpt.get();
        List<Product> products = productRepository.findAll();

        // Filter products by category
        List<Product> filtered = products.stream()
                .filter(p -> p.getCategory() != null && p.getCategory().getId().equals(category.getId()))
                .toList();

        if (filtered.isEmpty()) {
            System.out.println("⚠️ No products found in category: " + category.getName());
        } else {
            System.out.println("✅ Products in category: " + category.getName());
            Printer.printProducts(filtered);
        }
    }

    public void printProductsBySupplier() {
        System.out.println("🔎 Filter Products by Supplier");

        // Show all suppliers first
        supplierService.printAllSuppliers();
        int supplierId = Helper.getIntFromUser("Enter Supplier ID to filter products");

        Optional<Supplier> supplierOpt = Optional.ofNullable(supplierRepository.findById(supplierId));
        if (supplierOpt.isEmpty()) {
            System.out.println("❌ Supplier not found. Aborting filter.");
            return;
        }

        Supplier supplier = supplierOpt.get();
        List<Product> products = productRepository.findAll();

        // Filter products by supplier
        List<Product> filtered = products.stream()
                .filter(p -> p.getSupplier() != null && p.getSupplier().getId().equals(supplier.getId()))
                .toList();

        if (filtered.isEmpty()) {
            System.out.println("⚠️ No products found for supplier: " + supplier.getName());
        } else {
            System.out.println("✅ Products supplied by: " + supplier.getName());
            Printer.printProducts(filtered);
        }
    }

    public void printLowStockProducts() {
        System.out.println("📉 Filter Products by Stock Threshold");

        int threshold = Helper.getIntFromUser("Enter stock threshold value");

        List<Product> products = productRepository.findAll();

        List<Product> lowStock = products.stream()
                .filter(p -> p.getStock() < threshold)
                .toList();

        if (lowStock.isEmpty()) {
            System.out.println("✅ All products have stock above " + threshold);
        } else {
            System.out.println("⚠️ Products with stock below " + threshold + ":");
            Printer.printProducts(lowStock);
        }
    }

    public void printProductsByPriceRange() {
        System.out.println("💰 Filter Products by Price Range");

        BigDecimal minPrice = Helper.getBigDecimalFromUser("Enter minimum price");
        BigDecimal maxPrice = Helper.getBigDecimalFromUser("Enter maximum price");

        if (minPrice.compareTo(maxPrice) > 0) {
            System.out.println("❌ Minimum price cannot be greater than maximum price. Aborting filter.");
            return;
        }

        List<Product> products = productRepository.findAll();

        List<Product> filtered = products.stream()
                .filter(p -> p.getPrice().compareTo(minPrice) >= 0 &&
                        p.getPrice().compareTo(maxPrice) <= 0)
                .toList();

        if (filtered.isEmpty()) {
            System.out.println("⚠️ No products found in the price range " + minPrice + " - " + maxPrice);
        } else {
            System.out.println("✅ Products in the price range " + minPrice + " - " + maxPrice + ":");
            Printer.printProducts(filtered);
        }
    }

    public void printProductsByPopularity() {
        System.out.println("⭐ Filter Products by Popularity (Units Sold)");

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

        // 4. Sort products by total units sold (descending)
        List<Map.Entry<Product, Integer>> sorted = totalsByProduct.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .toList();

        // 5. Print results
        System.out.println("📊 Products ranked by popularity (units sold):");
        int rank = 1;
        for (Map.Entry<Product, Integer> entry : sorted) {
            Product product = entry.getKey();
            int totalQty = entry.getValue();
            System.out.println(rank + ". 📦 " + product.getName() +
                    " | Units Sold: " + totalQty);
            rank++;
        }
    }

    public void printProductsByLastSaleDate() {
        System.out.println("📅 Filter Products by Last Sale Date");

        // 1. Ask user how many days back to consider
        int days = Helper.getIntFromUser("Enter number of days (e.g., 30 for last month)");

        if (days <= 0) {
            System.out.println("❌ Invalid number of days.");
            return;
        }

        LocalDate cutoffDate = LocalDate.now().minusDays(days);

        // 2. Get all sales
        List<Sale> sales = saleRepository.findAll();
        if (sales.isEmpty()) {
            System.out.println("⚠️ No sales found.");
            return;
        }

        // 3. Map product → last sale date
        Map<Product, LocalDate> lastSaleByProduct = new HashMap<>();

        for (Sale sale : sales) {
            if (sale.getSaleDate() == null || sale.getItems() == null) continue;

            for (SaleItem item : sale.getItems()) {
                Product product = item.getProduct();
                if (product == null) continue;

                LocalDate saleDate = sale.getSaleDate();
                lastSaleByProduct.merge(product, saleDate,
                        (oldDate, newDate) -> newDate.isAfter(oldDate) ? newDate : oldDate);
            }
        }

        // 4. Filter products with last sale date after cutoff
        List<Map.Entry<Product, LocalDate>> recentSales = lastSaleByProduct.entrySet().stream()
                .filter(e -> !e.getValue().isBefore(cutoffDate))
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // newest first
                .toList();

        // 5. Print results
        if (recentSales.isEmpty()) {
            System.out.println("⚠️ No products sold in the last " + days + " days.");
        } else {
            System.out.println("✅ Products sold in the last " + days + " days:");
            for (Map.Entry<Product, LocalDate> entry : recentSales) {
                System.out.println("📦 " + entry.getKey().getName() +
                        " | Last Sale: " + entry.getValue());
            }
        }
    }

    public void printSlowMovingProducts() {
        System.out.println("🐢 Filter Slow-Moving Products (Not Sold in Last X Days)");

        // 1. Ask user how many days back to check
        int days = Helper.getIntFromUser("Enter number of days (e.g., 60 for last 2 months)");

        if (days <= 0) {
            System.out.println("❌ Invalid number of days.");
            return;
        }

        LocalDate cutoffDate = LocalDate.now().minusDays(days);

        // 2. Get all products
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            System.out.println("⚠️ No products found.");
            return;
        }

        // 3. Get all sales
        List<Sale> sales = saleRepository.findAll();

        // 4. Map product → last sale date
        Map<Product, LocalDate> lastSaleByProduct = new HashMap<>();

        for (Sale sale : sales) {
            if (sale.getSaleDate() == null || sale.getItems() == null) continue;

            for (SaleItem item : sale.getItems()) {
                Product product = item.getProduct();
                if (product == null) continue;

                LocalDate saleDate = sale.getSaleDate();
                lastSaleByProduct.merge(product, saleDate,
                        (oldDate, newDate) -> newDate.isAfter(oldDate) ? newDate : oldDate);
            }
        }

        // 5. Filter products with no sales OR last sale before cutoff
        List<Product> slowMoving = products.stream()
                .filter(p -> {
                    LocalDate lastSale = lastSaleByProduct.get(p);
                    return lastSale == null || lastSale.isBefore(cutoffDate);
                })
                .toList();

        // 6. Print results
        if (slowMoving.isEmpty()) {
            System.out.println("✅ All products have been sold in the last " + days + " days.");
        } else {
            System.out.println("🐢 Slow-moving products (not sold in last " + days + " days):");
            Printer.printProducts(slowMoving);
        }
    }

    public void printProductsByCategoryAndPriceRange() {
        System.out.println("🏷️💲 Filter Products by Category + Price Range");

        // 1. Show all categories so user can choose
        categoryService.printAllCategories();
        Long categoryId = Helper.getLongFromUser("Enter Category ID");

        Optional<Category> categoryOpt = Optional.ofNullable(categoryRepository.findById(categoryId));
        if (categoryOpt.isEmpty()) {
            System.out.println("❌ Category not found. Aborting filter.");
            return;
        }
        Category category = categoryOpt.get();

        // 2. Ask for price range
        double minPrice = Helper.getDoubleFromUser("Enter minimum price");
        double maxPrice = Helper.getDoubleFromUser("Enter maximum price");

        if (minPrice > maxPrice) {
            System.out.println("❌ Minimum price cannot be greater than maximum price.");
            return;
        }

        // 3. Get all products
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            System.out.println("⚠️ No products found.");
            return;
        }

        // 4. Filter by category AND price range
        List<Product> filtered = products.stream()
                .filter(p -> p.getCategory() != null && p.getCategory().getId() == category.getId())
                .filter(p -> p.getPrice() != null &&
                        p.getPrice().doubleValue() >= minPrice &&
                        p.getPrice().doubleValue() <= maxPrice)
                .toList();

        // 5. Print results
        if (filtered.isEmpty()) {
            System.out.println("⚠️ No products found in category '" + category.getName() +
                    "' within price range " + minPrice + " - " + maxPrice);
        } else {
            System.out.println("✅ Products in category '" + category.getName() +
                    "' within price range " + minPrice + " - " + maxPrice + ":");
            Printer.printProducts(filtered);
        }
    }

    public void printProductsBySupplierAndLowStock() {
        System.out.println("🚚📉 Filter Products by Supplier + Low Stock");

        // 1. Show all suppliers so user can choose
        supplierService.printAllSuppliers();
        int supplierId = Helper.getIntFromUser("Enter Supplier ID");

        Optional<Supplier> supplierOpt = Optional.ofNullable(supplierRepository.findById(supplierId));
        if (supplierOpt.isEmpty()) {
            System.out.println("❌ Supplier not found. Aborting filter.");
            return;
        }
        Supplier supplier = supplierOpt.get();

        // 2. Ask for low stock threshold
        int threshold = Helper.getIntFromUser("Enter stock threshold (e.g., 5)");

        if (threshold < 0) {
            System.out.println("❌ Threshold cannot be negative.");
            return;
        }

        // 3. Get all products
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            System.out.println("⚠️ No products found.");
            return;
        }

        // 4. Filter by supplier AND stock below threshold
        List<Product> filtered = products.stream()
                .filter(p -> p.getSupplier() != null && p.getSupplier().getId() == supplier.getId())
                .filter(p -> p.getStock() <= threshold)
                .toList();

        // 5. Print results
        if (filtered.isEmpty()) {
            System.out.println("✅ No low-stock products found for supplier: " + supplier.getName());
        } else {
            System.out.println("⚠️ Low-stock products from supplier: " + supplier.getName());
            Printer.printProducts(filtered);
        }
    }

    public void printPopularLowStockProducts() {
        System.out.println("⭐📉 Filter Products by Popularity + Low Stock");

        // 1. Ask for low stock threshold
        int threshold = Helper.getIntFromUser("Enter stock threshold (e.g., 5)");

        if (threshold < 0) {
            System.out.println("❌ Threshold cannot be negative.");
            return;
        }

        // 2. Get all sales
        List<Sale> sales = saleRepository.findAll();
        if (sales.isEmpty()) {
            System.out.println("⚠️ No sales found.");
            return;
        }

        // 3. Flatten all sale items
        List<SaleItem> allItems = sales.stream()
                .filter(s -> s.getItems() != null)
                .flatMap(s -> s.getItems().stream())
                .toList();

        if (allItems.isEmpty()) {
            System.out.println("⚠️ No sale items found.");
            return;
        }

        // 4. Group by product and sum quantities sold
        Map<Product, Integer> totalsByProduct = allItems.stream()
                .filter(item -> item.getProduct() != null)
                .collect(Collectors.groupingBy(
                        SaleItem::getProduct,
                        Collectors.summingInt(SaleItem::getQuantity)
                ));

        // 5. Filter products that are low in stock
        List<Map.Entry<Product, Integer>> popularLowStock = totalsByProduct.entrySet().stream()
                .filter(e -> e.getKey().getStock() <= threshold)
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue())) // sort by popularity
                .toList();

        // 6. Print results
        if (popularLowStock.isEmpty()) {
            System.out.println("✅ No popular products are currently low in stock.");
        } else {
            System.out.println("⚠️ Popular products running low (stock ≤ " + threshold + "):");
            int rank = 1;
            for (Map.Entry<Product, Integer> entry : popularLowStock) {
                Product product = entry.getKey();
                int totalSold = entry.getValue();
                System.out.println(rank + ". 📦 " + product.getName() +
                        " | Units Sold: " + totalSold +
                        " | Current Stock: " + product.getStock());
                rank++;
            }
        }
    }

    public void printTop5ProductsByRevenue() {
        System.out.println("💰 Top 5 Products by Revenue (including ties)");

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

        // 3. Group by product and sum revenue (price × quantity)
        Map<Product, BigDecimal> revenueByProduct = allItems.stream()
                .filter(item -> item.getProduct() != null && item.getProduct().getPrice() != null)
                .collect(Collectors.groupingBy(
                        SaleItem::getProduct,
                        Collectors.reducing(BigDecimal.ZERO,
                                item -> item.getProduct().getPrice()
                                        .multiply(BigDecimal.valueOf(item.getQuantity())),
                                BigDecimal::add)
                ));

        // 4. Sort products by revenue (descending)
        List<Map.Entry<Product, BigDecimal>> sorted = revenueByProduct.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .toList();

        if (sorted.isEmpty()) {
            System.out.println("⚠️ No products with revenue found.");
            return;
        }

        // 5. Determine cutoff (5th place revenue)
        int limit = Math.min(5, sorted.size());
        BigDecimal cutoffValue = sorted.get(limit - 1).getValue();

        // 6. Include all products with revenue >= cutoff
        List<Map.Entry<Product, BigDecimal>> topWithTies = sorted.stream()
                .filter(e -> e.getValue().compareTo(cutoffValue) >= 0)
                .toList();

        // 7. Print results
        int rank = 1;
        for (Map.Entry<Product, BigDecimal> entry : topWithTies) {
            Product product = entry.getKey();
            BigDecimal revenue = entry.getValue();
            System.out.println(rank + ". 📦 " + product.getName() +
                    " | Total Revenue: " + revenue + " EUR");
            rank++;
        }
    }

    public void printTop5BestSellingProducts() {
        System.out.println("🏆 Top 5 Best-Selling Products (including ties)");

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

        // 3. Group by product and sum quantities sold
        Map<Product, Integer> totalsByProduct = allItems.stream()
                .filter(item -> item.getProduct() != null)
                .collect(Collectors.groupingBy(
                        SaleItem::getProduct,
                        Collectors.summingInt(SaleItem::getQuantity)
                ));

        // 4. Sort products by total units sold (descending)
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
                    " | Units Sold: " + totalQty);
            rank++;
        }
    }


}
