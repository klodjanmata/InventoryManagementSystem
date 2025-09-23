import Repository.*;
import Service.*;
import Util.Helper;

public class Application {

    private static ApplicationManager applicationManager = new ApplicationManager();

    private ProductRepository productRepository;
    private SupplierRepository supplierRepository;
    private SaleRepository saleRepository;
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private EmployeeRepository employeeRepository;
    private SaleItemRepository saleItemRepository;

    private CustomerService customerService;
    private CategoryService categoryService;
    private EmployeeService employeeService;
    private ProductService productService;
    private SupplierService supplierService;
    private SaleService saleService;
    private SaleItemService saleItemService;

    public Application() {
        productRepository = new ProductRepository();
        supplierRepository = new SupplierRepository();
        saleRepository = new SaleRepository();
        customerRepository = new CustomerRepository();
        categoryRepository = new CategoryRepository();
        employeeRepository = new EmployeeRepository();
        saleItemRepository = new SaleItemRepository();

        customerService = new CustomerService();
        categoryService = new CategoryService();
        employeeService = new EmployeeService();
        productService = new ProductService();
        supplierService = new SupplierService();
        saleService = new SaleService();
        saleItemService = new SaleItemService();
    }


    public static void main(String[] args) {
        Application application = new Application();
        while (true) {
            Menu.mainMenu();
            if (manageAction(getChoice(), application)){
                System.out.println("See you later!! BYE BYE");
                return;
            }
        }
    }


    private static int getChoice() {
        while(true){
            try{
                int choice = Helper.getIntFromUser("Please enter the number of the choice");
                return choice;
            }catch(Exception e){
                System.out.println("Invalid input!TryAgain!");
            }
        }
    }

    private static boolean manageAction(int choice, Application application){
        switch (choice){
            case 1:
                Menu.customerMenu();
                int customerChoice = getChoice();
                applicationManager.customerMenuSelection(customerChoice);
                break;

            case 2:
                Menu.categoryMenu();
                int categoryChoice = getChoice();
                applicationManager.categoryMenuSelection(categoryChoice);
                break;

            case 3:
                Menu.employeeMenu();
                int employeeChoice = getChoice();
                applicationManager.employeeMenuSelection(employeeChoice);
                break;

            case 4:
                Menu.productMenu();
                int productChoice = getChoice();
                applicationManager.productMenuSelection(productChoice);
                break;

            case 6:
                Menu.supplierMenu();
                int supplierChoice = getChoice();
                applicationManager.supplierMenuSelection(supplierChoice);
                break;

            case 0:
                return true;
            default:
                System.out.println("Invalid input! Try again!");
        }
        return false;
    }
}
