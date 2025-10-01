import Entity.Category;
import Entity.SaleItem;
import Repository.*;
import Service.*;
import Util.Helper;

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

    // ---------------- CUSTOMER MENU ----------------
    public void customerMenuSelection(int choice) {
        boolean back = false;
        while (!back) {
            Menu.customerMenu();
            choice = Helper.getIntFromUser("Please enter the number of the choice");
            switch (choice) {
                case 1: customerService.registerCustomer(); break;
                case 2: customerService.printAllCustomers(); break;
                case 3: customerService.updateCustomer(); break;
                case 4: customerService.deleteCustomer(); break;
                case 0: back = true; break;
                default: System.out.println("Unsupported choice! Try again!");
            }
        }
    }

    // ---------------- CATEGORY MENU ----------------
    public void categoryMenuSelection(int choice) {
        boolean back = false;
        while (!back) {
            Menu.categoryMenu();
            choice = Helper.getIntFromUser("Please enter the number of the choice");
            switch (choice) {
                case 1: categoryService.addCategory(); break;
                case 2: categoryService.printAllCategories(); break;
                case 3: categoryService.updateCategory(); break;
                case 4: categoryService.deleteCategory(); break;
                case 0: back = true; break;
                default: System.out.println("Unsupported choice! Try again!");
            }
        }
    }

    // ---------------- EMPLOYEE MENU ----------------
    public void employeeMenuSelection(int choice) {
        boolean back = false;
        while (!back) {
            Menu.employeeMenu();
            choice = Helper.getIntFromUser("Please enter the number of the choice");
            switch (choice) {
                case 1: employeeService.hireEmployee(); break;
                case 2: employeeService.printAllEmployees(); break;
                case 3: employeeService.updateEmployee(); break;
                case 4: employeeService.fireEmployee(); break;
                case 0: back = true; break;
                default: System.out.println("Unsupported choice! Try again!");
            }
        }
    }

    // ---------------- PRODUCT MENU ----------------
    public void productMenuSelection(int choice) {
        boolean back = false;
        while (!back) {
            Menu.productMenu();
            choice = Helper.getIntFromUser("Please enter the number of the choice");
            switch (choice) {
                case 1: productService.addProduct(); break;
                case 2: productService.printAllProducts(); break;
                case 3: productService.updateProduct(); break;
                case 4: productService.deleteProduct(); break;
                case 0: back = true; break;
                default: System.out.println("Unsupported choice! Try again!");
            }
        }
    }

    // ---------------- SALES MENU ----------------
    public void saleMenuSelection(int choice) {
        boolean back = false;
        while (!back) {
            Menu.saleMenu();
            choice = Helper.getIntFromUser("Please enter the number of the choice");
            switch (choice) {
                case 1: saleService.makeSale(); break;
                case 2: saleService.printAllSales(); break;
                case 3: saleService.printSaleDetails(); break;
                case 4: saleService.deleteSale(); break;
                case 0: back = true; break;
                default: System.out.println("Unsupported choice! Try again!");
            }
        }
    }

    // ---------------- SUPPLIER MENU ----------------
    public void supplierMenuSelection(int choice) {
        boolean back = false;
        while (!back) {
            Menu.supplierMenu();
            choice = Helper.getIntFromUser("Please enter the number of the choice");
            switch (choice) {
                case 1: supplierService.addSupplier(); break;
                case 2: supplierService.printAllSuppliers(); break;
                case 3: supplierService.updateSupplier(); break;
                case 4: supplierService.deleteSupplier(); break;
                case 0: back = true; break;
                default: System.out.println("Unsupported choice! Try again!");
            }
        }
    }

    // ---------------- FILTERING MENU ----------------
    public void filteringMenuSelection(int choice) {
        boolean back = false;
        while (!back) {
            Menu.filteringMenu();
            choice = Helper.getIntFromUser("Please enter the number of the choice");
            switch (choice) {
                case 1:
                    boolean backToMain = false;
                    while (!backToMain) {
                        Menu.filteringProductMenu();
                        int choice1 = Helper.getIntFromUser("Please enter the number of the choice");
                        backToMain = filteringProductSelection(choice1);
                    }
                    break;

                case 2:
                    boolean backToMainSales = false;
                    while (!backToMainSales) {
                        Menu.filteringSalesMenu();
                        int choice2 = Helper.getIntFromUser("Please enter the number of the choice");
                        backToMainSales = filteringSalesSelection(choice2);
                    }
                    break;

                case 0:
                    back = true;
                    break;

                default:
                    System.out.println("Unsupported choice! Try again!");
            }
        }
    }

    public boolean filteringProductSelection(int choice) {
        switch (choice) {
            case 1: productService.printProductsByCategory(); break;
            case 2: productService.printProductsBySupplier(); break;
            case 3: productService.printLowStockProducts(); break;
            case 4: productService.printProductsByPriceRange(); break;
            case 0: return true;
            default: System.out.println("Unsupported choice! Try again!");
        }
        return false;
    }

    public boolean filteringSalesSelection(int choice) {
        switch (choice) {
            case 1: saleService.printSalesByDateRange(); break;
            case 2: saleService.printSalesByCustomer(); break;
            case 3: saleService.printSalesByAmount(); break;
            case 0: return true;
            default: System.out.println("Unsupported choice! Try again!");
        }
        return false;
    }
}