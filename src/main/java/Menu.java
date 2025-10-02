public class Menu {

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

    public static void customerMenu() {
        System.out.println("\n=== 👤 CUSTOMER MENU ===");
        System.out.println("1. ➕ Add Customer");
        System.out.println("2. 📋 List All Customers");
        System.out.println("3. ✏️ Edit Customer");
        System.out.println("4. 🗑️ Delete Customer");
        System.out.println("0. 🔙 Back");
        System.out.println("----------------------");
    }

    public static void categoryMenu() {
        System.out.println("\n=== 🏷️ CATEGORY MENU ===");
        System.out.println("1. ➕ Add Category");
        System.out.println("2. 📋 List All Categories");
        System.out.println("3. ✏️ Edit Category");
        System.out.println("4. 🗑️ Delete Category");
        System.out.println("0. 🔙 Back");
        System.out.println("----------------------");
    }

    public static void employeeMenu() {
        System.out.println("\n=== 👔 EMPLOYEE MENU ===");
        System.out.println("1. ➕ Add Employee");
        System.out.println("2. 📋 List All Employees");
        System.out.println("3. ✏️ Edit Employee");
        System.out.println("4. 🗑️ Delete Employee");
        System.out.println("0. 🔙 Back");
        System.out.println("----------------------");
    }

    public static void productMenu() {
        System.out.println("\n=== 📦 PRODUCT MENU ===");
        System.out.println("1. ➕ Add Product");
        System.out.println("2. 📋 List All Products");
        System.out.println("3. ✏️ Edit Product");
        System.out.println("4. 🗑️ Delete Product");
        System.out.println("5. ✏️ Update Product Stock");
        System.out.println("0. 🔙 Back");
        System.out.println("----------------------");
    }

    public static void saleMenu() {
        System.out.println("\n=== 💰 SALES MENU ===");
        System.out.println("1. 🛒 Make a Sale");
        System.out.println("2. 📋 List All Sales");
        System.out.println("3. 🧾 Print Sale Details");
        System.out.println("4. 🗑️ Cancel Sale");
        System.out.println("0. 🔙 Back");
        System.out.println("----------------------");
    }

    public static void supplierMenu() {
        System.out.println("\n=== 🚚 SUPPLIER MENU ===");
        System.out.println("1. ➕ Add Supplier");
        System.out.println("2. 📋 List All Suppliers");
        System.out.println("3. ✏️ Edit Supplier");
        System.out.println("4. 🗑️ Delete Supplier");
        System.out.println("0. 🔙 Back");
        System.out.println("----------------------");
    }

    public static void filteringMenu() {
        System.out.println("\n=== 🔎 FILTERING MENU ===");
        System.out.println("1. 📦 Filtering Products");
        System.out.println("2. 💰 Filtering Sales");
        System.out.println("3. 👤 Filtering Customers");
        System.out.println("4. 🏷️ Filtering Categories");
        System.out.println("5. 🧑‍💼 Filtering Employees");
        System.out.println("6. 🚚 Filtering Suppliers");
        System.out.println("0. 🔙 Back");
        System.out.println("----------------------");
    }

    public static void filteringProductMenu() {
        System.out.println("\n=== 📦 FILTERING PRODUCTS ===");
        System.out.println("1. 🏷️ Filter Products By Category");
        System.out.println("2. 🚚 Filter Products By Supplier");
        System.out.println("3. 📉 Filter Products By Stock");
        System.out.println("4. 💲 Filter Products By Price Range");
        System.out.println("5. ⭐ Filter Products by Popularity (Units Sold)");
        System.out.println("6. 📅 Filter Products by Last Sale Date");
        System.out.println("7. 🐢 Filter Slow-Moving Products (Not Sold in Last X Days)");
        System.out.println("8. 🏷️💲 Filter by Category + Price Range");
        System.out.println("9. 🚚📉 Filter by Supplier + Low Stock");
        System.out.println("10. ⭐📉 Filter by Popularity + Low Stock");
        System.out.println("11. 🏆 Top 5 Best-Selling Products");
        System.out.println("12. 💰 Top 5 Products by Revenue");
        System.out.println("0. 🔙 Back");
        System.out.println("---------------------------");
    }

    public static void filteringSalesMenu() {
        System.out.println("\n=== 📊 FILTERING SALES ===");
        System.out.println("1. 📅 Filter Sales by Date Range");
        System.out.println("2. 👤 Filter Sales by Customer");
        System.out.println("3. 💰 Filter Sales by Total Amount");
        System.out.println("4. 🏷️ Filter Sales by Product Category");
        System.out.println("5. 📦 Filter Sales by Specific Product");
        System.out.println("6. 👤💰 Filter Sales by Customer and Amount Range");
        System.out.println("7. 🏆 Top 5 Customers by Sales Volume");
        System.out.println("8. 🏆 Top 5 Products by Sales Volume");
        System.out.println("9. 📅 Smart Filter: Most Frequent Sale Days");
        System.out.println("0. 🔙 Back");
        System.out.println("-----------------------------");
    }

    public static void filteringCustomerMenu(){
        System.out.println("=== 👤 FILTERING CUSTOMERS ===");
        System.out.println("1. 🔎 Filter Customers by Name");
        System.out.println("2. 📧 Filter Customers by Email Domain");
        System.out.println("3. 📱 Filter Customers by Phone Prefix");
        System.out.println("0. 🔙 Back");
    }

    public static void filteringCategoryMenu(){
        System.out.println("=== 📂 FILTERING CATEGORIES ===");
        System.out.println("1. 🔎 Filter Categories by Name");
        System.out.println("2. 📝 Filter Categories by Description Keyword");
        System.out.println("3. ⚠️ Filter Categories Missing Description");
        System.out.println("0. 🔙 Back");
    }

    public static void filteringEmployeeMenu() {
        System.out.println("=== 🧑‍💼 FILTERING EMPLOYEES ===");
        System.out.println("1. 🏷️ Filter Employees by Role");
        System.out.println("2. 📅 Filter Employees by Hire Date Range");
        System.out.println("3. 🔎 Filter Employees by Name");
        System.out.println("0. 🔙 Back");
    }

    public static void filteringSupplierMenu() {
        System.out.println("=== 🚚 FILTERING SUPPLIERS ===");
        System.out.println("1. 🔎 Filter Suppliers by Name");
        System.out.println("2. 👤 Filter Suppliers by Contact Person");
        System.out.println("3. 📧 Filter Suppliers by Email Domain");
        System.out.println("0. 🔙 Back");
    }
}