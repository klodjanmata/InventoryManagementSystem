public class Menu {

    // ---------------- MAIN MENU ----------------
    public static void mainMenu() {
        System.out.println("\n=== 🗂️ Inventory Management System ===");
        System.out.println("           *** MAIN MENU ***");
        System.out.println("1. 👤 Customer Menu");
        System.out.println("2. 🏷️ Category Menu");
        System.out.println("3. 👔 Employee Menu");
        System.out.println("4. 📦 Product Menu");
        System.out.println("5. 💰 Sales Menu");
        System.out.println("6. 🚚 Supplier Menu");
        System.out.println("7. 🔎 Filtering Menu");
        System.out.println("0. ❌ Exit");
        System.out.println("-----------------------------------");
    }

    // ---------------- CUSTOMER MENU ----------------
    public static void customerMenu() {
        System.out.println("\n=== 👤 CUSTOMER MENU ===");
        System.out.println("1. ➕ Add Customer");
        System.out.println("2. 📋 List All Customers");
        System.out.println("3. ✏️ Edit Customer");
        System.out.println("4. 🗑️ Delete Customer");
        System.out.println("0. 🔙 Back");
        System.out.println("----------------------");
    }

    // ---------------- CATEGORY MENU ----------------
    public static void categoryMenu() {
        System.out.println("\n=== 🏷️ CATEGORY MENU ===");
        System.out.println("1. ➕ Add Category");
        System.out.println("2. 📋 List All Categories");
        System.out.println("3. ✏️ Edit Category");
        System.out.println("4. 🗑️ Delete Category");
        System.out.println("0. 🔙 Back");
        System.out.println("----------------------");
    }

    // ---------------- EMPLOYEE MENU ----------------
    public static void employeeMenu() {
        System.out.println("\n=== 👔 EMPLOYEE MENU ===");
        System.out.println("1. ➕ Add Employee");
        System.out.println("2. 📋 List All Employees");
        System.out.println("3. ✏️ Edit Employee");
        System.out.println("4. 🗑️ Delete Employee");
        System.out.println("0. 🔙 Back");
        System.out.println("----------------------");
    }

    // ---------------- PRODUCT MENU ----------------
    public static void productMenu() {
        System.out.println("\n=== 📦 PRODUCT MENU ===");
        System.out.println("1. ➕ Add Product");
        System.out.println("2. 📋 List All Products");
        System.out.println("3. ✏️ Edit Product");
        System.out.println("4. 🗑️ Delete Product");
        System.out.println("0. 🔙 Back");
        System.out.println("----------------------");
    }

    // ---------------- SALES MENU ----------------
    public static void saleMenu() {
        System.out.println("\n=== 💰 SALES MENU ===");
        System.out.println("1. 🛒 Make a Sale");
        System.out.println("2. 📋 List All Sales");
        System.out.println("3. 🧾 Print Sale Details");
        System.out.println("4. 🗑️ Cancel Sale");
        System.out.println("0. 🔙 Back");
        System.out.println("----------------------");
    }

    // ---------------- SUPPLIER MENU ----------------
    public static void supplierMenu() {
        System.out.println("\n=== 🚚 SUPPLIER MENU ===");
        System.out.println("1. ➕ Add Supplier");
        System.out.println("2. 📋 List All Suppliers");
        System.out.println("3. ✏️ Edit Supplier");
        System.out.println("4. 🗑️ Delete Supplier");
        System.out.println("0. 🔙 Back");
        System.out.println("----------------------");
    }

    // ---------------- FILTERING MENU ----------------
    public static void filteringMenu() {
        System.out.println("\n=== 🔎 FILTERING MENU ===");
        System.out.println("1. 📦 Filtering Products");
        System.out.println("2. 💰 Filtering Sales");
        System.out.println("0. 🔙 Back");
        System.out.println("----------------------");
    }

    // ---------------- FILTERING PRODUCT MENU ----------------
    public static void filteringProductMenu() {
        System.out.println("\n=== 📦 FILTERING PRODUCTS ===");
        System.out.println("1. 🏷️ Filter Products By Category");
        System.out.println("2. 🚚 Filter Products By Supplier");
        System.out.println("3. 📉 Filter Products By Stock");
        System.out.println("4. 💲 Filter Products By Price Range");
        System.out.println("0. 🔙 Back");
        System.out.println("---------------------------");
    }

    // ---------------- FILTERING SALES MENU ----------------
    public static void filteringSalesMenu() {
        System.out.println("\n=== 📊 SALES FILTERING MENU ===");
        System.out.println("1. 📅 Filter Sales by Date Range");
        System.out.println("2. 👤 Filter Sales by Customer");
        System.out.println("3. 💰 Filter Sales by Total Amount");
        System.out.println("0. 🔙 Back");
        System.out.println("-----------------------------");
    }
}