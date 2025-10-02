
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
                case 5: productService.updateProductStockIncremental(); break;
                case 0: back = true; break;
                default: System.out.println("Unsupported choice! Try again!");
            }
        }
    }

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

                case 3:
                    boolean backToMainCustomer = false;
                    while (!backToMainCustomer) {
                        Menu.filteringCustomerMenu();
                        int choice3 = Helper.getIntFromUser("Please enter the number of the choice");
                        backToMainCustomer = filteringCustomerSelection(choice3);
                    }
                    break;

                case 4:
                    boolean backToMainCategory = false;
                    while (!backToMainCategory) {
                        Menu.filteringCategoryMenu();
                        int choice4 = Helper.getIntFromUser("Please enter the number of the choice");
                        backToMainCategory = filteringCategorySelection(choice4);
                    }
                    break;

                case 5:
                    boolean backToMainEmployee = false;
                    while (!backToMainEmployee) {
                        Menu.filteringEmployeeMenu();
                        int choice5 = Helper.getIntFromUser("Please enter the number of the choice");
                        backToMainEmployee = filteringEmployeeSelection(choice5);
                    }
                    break;

                case 6:
                    boolean backToMainSupplier = false;
                    while (!backToMainSupplier) {
                        Menu.filteringSupplierMenu();
                        int choice6 = Helper.getIntFromUser("Please enter the number of the choice");
                        backToMainSupplier = filteringSupplierSelection(choice6);
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
            case 5: productService.printProductsByPopularity(); break;
            case 6: productService.printProductsByLastSaleDate(); break;
            case 7: productService.printSlowMovingProducts(); break;
            case 8: productService.printProductsByCategoryAndPriceRange(); break;
            case 9: productService.printProductsBySupplierAndLowStock(); break;
            case 10: productService.printPopularLowStockProducts(); break;
            case 11: productService.printTop5BestSellingProducts(); break;
            case 12: productService.printTop5ProductsByRevenue(); break;
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
            case 4: saleService.printSalesByCategory(); break;
            case 5: saleService.printSalesByProduct(); break;
            case 6: saleService.printSalesByCustomerAndAmountRange(); break;
            case 7: saleService.printTop5CustomersBySalesVolume(); break;
            case 8: saleService.printTop5ProductsBySalesVolume(); break;
            case 9: saleService.printMostFrequentSaleDays(); break;
            case 0: return true;
            default: System.out.println("Unsupported choice! Try again!");
        }
        return false;
    }

    public boolean filteringCustomerSelection(int choice) {
        switch (choice) {
            case 1: customerService.filterCustomersByName(); break;
            case 2: customerService.filterCustomersByEmailDomain(); break;
            case 3: customerService.filterCustomersByPhonePrefix(); break;
            case 0: return true;
            default: System.out.println("Unsupported choice! Try again!");
        }
        return false;
    }

    public boolean filteringCategorySelection(int choice) {
        switch (choice) {
            case 1: categoryService.filterCategoriesByName(); break;
            case 2: categoryService.filterCategoriesByDescriptionKeyword(); break;
            case 3: categoryService.filterCategoriesByMissingDescription(); break;
            case 0: return true;
            default: System.out.println("Unsupported choice! Try again!");
        }
        return false;
    }

    public boolean filteringEmployeeSelection(int choice) {
        switch (choice) {
            case 1: employeeService.filterEmployeesByRole(); break;
            case 2: employeeService.filterEmployeesByHireDateRange(); break;
            case 3: employeeService.filterEmployeesByName(); break;
            case 0: return true;
            default: System.out.println("Unsupported choice! Try again!");
        }
        return false;
    }

    public boolean filteringSupplierSelection(int choice) {
        switch (choice) {
            case 1: supplierService.filterSuppliersByName(); break;
            case 2: supplierService.filterSuppliersByContactPerson(); break;
            case 3: supplierService.filterSuppliersByEmailDomain(); break;
            case 0: return true;
            default: System.out.println("Unsupported choice! Try again!");
        }
        return false;
    }
}