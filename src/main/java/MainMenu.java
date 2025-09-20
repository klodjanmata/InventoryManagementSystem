

import Service.CustomerService;
import Service.CategoryService;
import Service.EmployeeService;
import Service.ProductService;
import Service.SupplierService;
import Service.SaleService;

import java.util.Scanner;

public class MainMenu {

    private static final Scanner scanner = new Scanner(System.in);

    public static void Menu() {
        CustomerService customerService = new CustomerService();
        CategoryService categoryService = new CategoryService();
        EmployeeService employeeService = new EmployeeService();
        ProductService productService = new ProductService();
        SupplierService supplierService = new SupplierService();
        SaleService saleService = new SaleService();

        while (true) {
            System.out.println("\n=== Inventory Management System ===");
            System.out.println("1. Add Customer");
            System.out.println("2. List Customers");
            System.out.println("3. Add Category");
            System.out.println("4. List Categories");
            System.out.println("5. Hire Employee");
            System.out.println("6. List Employees");
            System.out.println("7. Add Product");
            System.out.println("8. List Products");
            System.out.println("9. Add Supplier");
            System.out.println("10. List Suppliers");
            System.out.println("11. Make Sale");
            System.out.println("12. List Sales");
            System.out.println("13. Exit");
            System.out.print("Choose an option: ");
        }
    }
}
