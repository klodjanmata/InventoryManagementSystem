package Util;

import Entity.*;

import java.util.ArrayList;
import java.util.List;

public class Printer {

    public static String customerHeader(){
        return "ID\tName\tEmail\tPhone";
    }

    public static String categoryHeader(){
        return "ID\tName\tDescription";
    }

    public static String employeeHeader(){
        return "ID\tName\tRole\tHire Date";
    }

    public static String productHeader(){
        return "ID\tName\tPrice\tStock\tSupplier\tCategory";
    }

    public static String supplierHeader(){
        return "ID\tName\tContact\tEmail";
    }





    public static void printCustomers(List<Customer> customers) {
        System.out.println(customerHeader());
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    public static void printCategories(List<Category> categories){
        System.out.println(categoryHeader());
        for (Category category : categories){
            System.out.println(category);
        }
    }

    public static void printEmployees(List<Employee> employees){
        System.out.println(employeeHeader());
        for (Employee employee : employees){
            System.out.println(employee);
        }
    }

    public static void printProducts(List<Product> products){
        System.out.println(productHeader());
        for (Product product : products){
            System.out.println(product);
        }
    }

    public static void printSuppliers(List<Supplier> suppliers){
        System.out.println(supplierHeader());
        for (Supplier supplier : suppliers){
            System.out.println(supplier);
        }
    }
}
