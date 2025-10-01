package Util;

import Entity.*;

import java.math.BigDecimal;
import java.util.List;

public class Printer {

    // -------------------- Customers --------------------
    public static void printCustomers(List<Customer> customers) {
        System.out.printf("%-5s %-20s %-25s %-15s%n",
                "ID", "Name", "Email", "Phone");
        for (Customer customer : customers) {
            System.out.printf("%-5d %-20s %-25s %-15s%n",
                    customer.getId(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getPhone());
        }
    }

    // -------------------- Categories --------------------
    public static void printCategories(List<Category> categories) {
        System.out.printf("%-5s %-20s %-40s%n",
                "ID", "Name", "Description");
        for (Category category : categories) {
            System.out.printf("%-5d %-20s %-40s%n",
                    category.getId(),
                    category.getName(),
                    category.getDescription());
        }
    }

    // -------------------- Employees --------------------
    public static void printEmployees(List<Employee> employees) {
        System.out.printf("%-5s %-20s %-20s %-15s%n",
                "ID", "Name", "Role", "Hire Date");
        for (Employee employee : employees) {
            System.out.printf("%-5d %-20s %-20s %-15s%n",
                    employee.getId(),
                    employee.getName(),
                    employee.getRole(),
                    employee.getHireDate());
        }
    }

    // -------------------- Products --------------------
    public static void printProducts(List<Product> products) {
        System.out.printf("%-5s %-25s %-10s %-8s %-20s %-15s%n",
                "ID", "Name", "Price", "Stock", "Supplier", "Category");
        for (Product product : products) {
            System.out.printf("%-5d %-25s %-10.2f %-8d %-20s %-15s%n",
                    product.getId(),
                    product.getName(),
                    product.getPrice().doubleValue(), // BigDecimal → double for formatting
                    product.getStock(),
                    product.getSupplier().getName(),
                    product.getCategory().getName());
        }
    }

    // -------------------- Suppliers --------------------
    public static void printSuppliers(List<Supplier> suppliers) {
        System.out.printf("%-5s %-25s %-20s %-25s%n",
                "ID", "Name", "Contact", "Email");
        for (Supplier supplier : suppliers) {
            System.out.printf("%-5d %-25s %-20s %-25s%n",
                    supplier.getId(),
                    supplier.getName(),
                    supplier.getContact(),
                    supplier.getEmail());
        }
    }

    // -------------------- Sales --------------------
    public static void printSales(List<Sale> sales) {
        System.out.printf("%-5s %-20s %-15s %-12s%n",
                "ID", "Customer", "Sale Date", "Total Amount");
        for (Sale sale : sales) {
            System.out.printf("%-5d %-20s %-15s %-12.2f%n",
                    sale.getId(),
                    sale.getCustomer().getName(),
                    sale.getSaleDate(),
                    sale.getTotalAmount().doubleValue());
        }
    }

    public static void printInvoice(Sale sale) {
        Customer customer = sale.getCustomer();

        System.out.println("===============================================");
        System.out.println("           📦 TECH SHOP INVOICE 📦             ");
        System.out.println("===============================================");
        System.out.println("🧾 Sale ID     : " + sale.getId());
        System.out.println("👤 Customer    : " + (customer != null ? customer.getId() + " - " + customer.getName() : "Unknown"));
        System.out.println("📅 Date        : " + sale.getSaleDate());
        System.out.println("-----------------------------------------------");
        System.out.println("🧺 Items:");
        System.out.printf("   %-20s %5s %10s %12s%n", "Product", "Qty", "Unit Price", "Subtotal");

        List<SaleItem> items = sale.getItems();
        if (items == null || items.isEmpty()) {
            System.out.println("   No items in this sale.");
        } else {
            for (SaleItem item : items) {
                Product product = item.getProduct();
                BigDecimal subtotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                System.out.printf("   %-20s %5d %10.2f %12.2f%n",
                        product.getName(),
                        item.getQuantity(),
                        item.getPrice(),
                        subtotal);
            }
        }

        System.out.println("-----------------------------------------------");
        System.out.printf("💰 TOTAL AMOUNT: %38.2f EUR%n", sale.getTotalAmount());
        System.out.println("===============================================");
        System.out.println("         ✅ Thank you for your purchase!        ");
        System.out.println("===============================================");
    }

}