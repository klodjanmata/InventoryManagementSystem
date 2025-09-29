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
        productService = new ProductService();
        saleItemRepository = new SaleItemRepository();
        saleItemService = new SaleItemService(saleItemRepository, productRepository);
        saleRepository = new SaleRepository();
        saleService = new SaleService();
        supplierRepository = new SupplierRepository();
        supplierService = new SupplierService();
    }



    public void customerMenuSelection(int choice) {

        switch (choice) {
            case 1:
                customerService.registerCustomer();
                break;

            case 2:
                customerService.printAllCustomers();
                break;

            case 3:
                customerService.updateCustomer();
                break;

            case 4:
                customerService.deleteCustomer();
                break;

            case 0:
                return;

            default:
                System.out.println("Unsupported choice! Try again!");
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

            case 3:
                categoryService.updateCategory();
                break;

            case 4:
                categoryService.deleteCategory();
                break;

            case 0:
                return;

            default:
                System.out.println("Unsupported choice! Try again!");
                break;


        }

    }



    public void employeeMenuSelection(int choice){
    switch (choice){
        case 1:
            employeeService.hireEmployee();
            break;

        case 2:
            employeeService.printAllEmployees();
            break;

        case 3:
            employeeService.updateEmployee();
            break;

        case 4:
            employeeService.fireEmployee();
            break;

        case 0:
            return;

        default:
            System.out.println("Unsupported choice! Try again!");
            break;
        }
    }

    public void productMenuSelection(int choice){
        switch (choice){
            case 1:
                productService.addProduct();
                break;

            case 2:
                productService.printAllProducts();
                break;

            case 3:
                productService.updateProduct();
                break;

            case 4:
                productService.deleteProduct();
                break;

            case 0:
                return;

            default:
                System.out.println("Unsupported choice! Try again!");
                break;
        }
    }

    public void saleMenuSelection(int choice){
        switch (choice){
            case 1:
                saleService.makeSale();
                break;
            case 2:
                saleService.printAllSales();
                break;
            case 3:
                saleService.printSaleDetails();

        }

    }

    public void supplierMenuSelection(int choice){
        switch (choice){
            case 1:
                supplierService.addSupplier();
                break;

            case 2:
                supplierService.printAllSuppliers();
                break;

            case 3:
                supplierService.updateSupplier();
                break;

            case 4:
                supplierService.deleteSupplier();
                break;

            case 0:
                return;

            default:
                System.out.println("Unsupported choice! Try again!");
                break;
        }
    }

}


