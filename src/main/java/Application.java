import Repository.ProductRepository;
import Repository.SaleRepository;
import Repository.SupplierRepository;
import Service.*;
import Util.Helper;

public class Application {

    private static ApplicationManager applicationManager = new ApplicationManager();

    private ProductRepository productRepository;
    private SupplierRepository supplierRepository;
    private SaleRepository saleRepository;

    private CustomerService customerService;
    private CategoryService categoryService;
    private EmployeeService employeeService;
    private ProductService productService;
    private SupplierService supplierService;
    private SaleService saleService;

    public Application() {
        productRepository = new ProductRepository();
        supplierRepository = new SupplierRepository();
        saleRepository = new SaleRepository();

        customerService = new CustomerService();
        categoryService = new CategoryService();
        employeeService = new EmployeeService();
        productService = new ProductService(productRepository);
        supplierService = new SupplierService(supplierRepository);
        saleService = new SaleService(saleRepository);
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
                application.categoryService.addCategory();
                break;
            case 4:
                application.categoryService.printAllCategories();
                break;
            case 0:
                return true;
            default:
                System.out.println("Invalid input! Try again!");
        }
        return false;
    }
}
