import Entity.Category;
import Entity.SaleItem;
import Repository.*;
import Service.*;

public class ApplicationManager {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;
    private CustomerRepository customerRepository;
    private CustomerService customerService;
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;
    private ProductRepository productRepository;
    private ProductService productService;
    private SaleItemRepository saleItemRepository;
    private SaleItemService saleItemService;
    private SaleRepository saleRepository;
    private SaleService saleService;
    private SupplierRepository supplierRepository;
    private SupplierService supplierService;

    public ApplicationManager() {
        categoryRepository = new CategoryRepository();
        categoryService = new CategoryService();
        customerRepository = new CustomerRepository();
        customerService = new CustomerService();
        employeeRepository = new EmployeeRepository();
        employeeService = new EmployeeService();
        productRepository = new ProductRepository();
        productService = new ProductService(productRepository);
        saleItemRepository = new SaleItemRepository();
        saleItemService = new SaleItemService(saleItemRepository);
        saleRepository = new SaleRepository();
        saleService = new SaleService(saleRepository);
        supplierRepository = new SupplierRepository();
        supplierService = new SupplierService(supplierRepository);

    }

    public void customerMenuSelection(int choice) {

        switch (choice) {
            case 1:
                customerService.registerCustomer();
                break;

            case 2:
                customerService.printAllCustomers();
                break;

            default:
                System.out.println("Unsupported filter choice! Try again!");
                break;


        }


    }

    public void categoryMenuSelection(int choice) {

        switch (choice) {
            case 1:
                categoryService.addCategory();
                break;

            case 2:
                categoryService.printAllCategories();
                break;

            default:
                System.out.println("Unsupported filter choice! Try again!");
                break;


        }

    }
}


