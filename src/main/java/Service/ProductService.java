package Service;

import Entity.Category;
import Entity.Product;
import Entity.Supplier;
import Repository.CategoryRepository;
import Repository.ProductRepository;
import Repository.SupplierRepository;
import Util.Helper;
import Util.Printer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductService {

    private ProductRepository productRepository = new ProductRepository();
    private SupplierRepository supplierRepository = new SupplierRepository();
    private SupplierService supplierService = new SupplierService();
    private CategoryRepository categoryRepository = new CategoryRepository();
    private CategoryService categoryService = new CategoryService();
    private Supplier supplier = new Supplier();
    private Category category = new Category();

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

    public Product getProductById(int id) {
        return productRepository.findById(id);
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
}
