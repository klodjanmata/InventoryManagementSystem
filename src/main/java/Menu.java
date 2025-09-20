public class Menu {

    public static void mainMenu() {
        System.out.println("\n=== Inventory Management System ===");
        System.out.println("1. Customer");
        System.out.println("2. Category");
        System.out.println("3. Employee");
        System.out.println("4. Product");
        System.out.println("5. Sale");
        System.out.println("6. Supplier");
        System.out.println("0. Exit");

    }

    public static void customerMenu(){
        System.out.println("1. Add Customer");
        System.out.println("2. List All Customer");
        System.out.println("3. Edit Customer");
        System.out.println("4. Delete Customer");
    }

    public static void categoryMenu(){
        System.out.println("1. Add Category");
        System.out.println("2. List All Categories");
        System.out.println("3. Edit Category");
        System.out.println("4. Delete Category");
    }
}
