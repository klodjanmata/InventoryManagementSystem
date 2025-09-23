package Service;

import Entity.Category;
import Entity.Product;
import Entity.Supplier;
import Repository.CategoryRepository;
import Repository.ProductRepository;
import Repository.SupplierRepository;
import Util.Helper;
import Util.Printer;

import java.util.List;
import java.util.Optional;

public class ProductService {

    private ProductRepository productRepository = new ProductRepository();
    private SupplierRepository supplierRepository = new SupplierRepository();
    private SupplierService supplierService = new SupplierService();
    private CategoryRepository categoryRepository = new CategoryRepository();
    private CategoryService categoryService = new CategoryService();

    public ProductService() {
        this.productRepository = productRepository;
    }

    public void addProduct() {
        Product product = new Product();
        System.out.println("📦 Provide product details");

        // Basic product info
        product.setName(Helper.getStringFromUser("Enter Product Name"));
        product.setPrice(Helper.getDoubleFromUser("Enter Product Price"));
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

    public void updateProduct(Product product) {
        productRepository.update(product);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id);
    }

    public void getAllProducts() {
        List<Product> products = productRepository.findAll();
        Printer.printProducts(products);
    }
}
