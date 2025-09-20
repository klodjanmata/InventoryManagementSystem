package Service;

import Entity.Product;
import Entity.Supplier;
import Repository.ProductRepository;
import Util.Helper;

import java.util.List;
import java.util.logging.SocketHandler;

public class ProductService {

    private ProductRepository productRepository = new ProductRepository();

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    public void addProduct() {
//        Product product = new Product();
//        System.out.println("Provide necessary parameters");
//        product.setName(Helper.getStringFromUser("Enter Product Name"));
//        product.setPrice(Helper.getFloatFromUser("Enter Product Price"));
//        product.setStock(Helper.getIntFromUser("Enter Stock Number"));
//
//
//
//    }

    public void updateProduct(Product product) {
        productRepository.update(product);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
